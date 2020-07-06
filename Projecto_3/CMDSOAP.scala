import java.io.{BufferedInputStream, BufferedReader, IOException, FileInputStream, FileOutputStream, InputStreamReader, OutputStreamWriter}
import java.net.{HttpURLConnection, URL}
import java.security.{DigestInputStream, KeyStore, MessageDigest, PublicKey, Signature}
import java.security.cert.CertificateFactory
import java.util.logging.{FileHandler, Logger, SimpleFormatter}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import java.util.Base64


object Main extends App {

  val APPLICATIONID = "YjgyNjM1OWMtMDZmOC00MjVlLThlYzMtNTBhOTdhNDE4OTE2"

  // LOGGER
  val logger = Logger.getLogger(getClass.getName)
  val fh = new FileHandler("cmd.log", true)
  logger.addHandler(fh)
  logger.setUseParentHandlers(false)
  val formatter = new SimpleFormatter
  fh.setFormatter(formatter)


  class CMDGetCertificate(var userId: String, var applicationId: String = APPLICATIONID) {

    //    val APPLICATIONID = "YjgyNjM1OWMtMDZmOC00MjVlLThlYzMtNTBhOTdhNDE4OTE2"
    private val SOAP_ACTION = "http://Ama.Authentication.Service/CCMovelSignature/GetCertificate"
    private val stringUrl = "https://cmd.autenticacao.gov.pt/Ama.Authentication.Frontend/CCMovelDigitalSignature.svc"

    def getUserId: String = userId

    def getApplicationId: String = applicationId

    def getCertificate: Array[String] = {
      val response = sendSOAPrequest
      if (response == "Error") die(msg = "Error getting certificates", args = "Error getting certificates")
      val certificates = parseResponse(response)
      certificates
    }

    private def parseResponse(response: String) = {
      val certificates = new Array[String](3)

      try {
        val splitedResp = response.split("</?GetCertificateResult>")
        val allCerts = splitedResp(1)
        val tempCerts = allCerts.split("-----BEGIN CERTIFICATE-----")

        val total = tempCerts.length
        var i = 0
        while ({i < total}) {
          tempCerts(i) = "-----BEGIN CERTIFICATE-----\n" + tempCerts(i)
          i += 1
        }
        //        val certificates = new Array[String](3)
        certificates(0) = tempCerts(1).replace("&#xD;", "\n")
        certificates(1) = tempCerts(3).replace("&#xD;", "\n")
        certificates(2) = tempCerts(2).replace("&#xD;", "\n")
        certificates

      } catch {
        case e: Exception =>
          logger.severe(s"Error $e")
          die(msg = "Error getting certificates", args = "Error getting certificates")
      }
    }

    def sendSOAPrequest: String = {
      var response = ""

      try {
        val body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
          "<soapenv:Header/>" + "<soapenv:Body>" +
          "<GetCertificate xmlns=\"http://Ama.Authentication.Service/\">" +
          "<applicationId>" + APPLICATIONID + "</applicationId>" +
          "<userId>" + userId + "</userId>" +
          "</GetCertificate>" +
          "</soapenv:Body>" +
          "</soapenv:Envelope>"

        val url = new URL(stringUrl)
        val conn = url.openConnection.asInstanceOf[HttpURLConnection]
        conn.setRequestMethod("POST")
        conn.setDoOutput(true)
        conn.setDefaultUseCaches(false)
        conn.setRequestProperty("Content-type", "text/xml")
        conn.setRequestProperty("SOAPAction", SOAP_ACTION)

        //push the request to the server address
        val wr = new OutputStreamWriter(conn.getOutputStream)
        wr.write(body)
        wr.flush()

        //get the server response
        val reader = new BufferedReader(new InputStreamReader(conn.getInputStream))
        val builder = new StringBuilder
        var line = ""
        while ( {line != null}) {

          line = reader.readLine()

          if (line != null) {
            builder.append(line)
            response = builder.toString
          }
        }
      } catch {
        case e: Exception =>
          logger.severe(s"Error $e")
          response = "Error"
      }

      response
    }

  }

  class CMDccMovelSign(var userId: String, var pin: String, var docName: String, var docHash: String, var applicationId: String = APPLICATIONID) {

    private val SOAP_ACTION = "http://Ama.Authentication.Service/CCMovelSignature/CCMovelSign"
    private val stringUrl = "https://cmd.autenticacao.gov.pt/Ama.Authentication.Frontend/CCMovelDigitalSignature.svc"

    def getUserId: String = userId

    def getDocName: String = docName

    def getPin: String = pin

    def getApplicationId: String = applicationId

    def getDocHash: String = docHash

    def ccMovelSign: String = {
      val response = sendSOAPrequest
      val msg = response.split("</?a:Message>")(1)
      if (response == "Error" || msg == "User does not have signature active")
        die(msg = "Error running CCMovelSign service", args = "Error running CCMovelSign service")
      val processId = parseResponse(response)
      processId
    }

    private def sendSOAPrequest = {
      var response = ""
      //TO DO
      try {
        val body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
          "<soapenv:Body>" + "<CCMovelSign xmlns=\"http://Ama.Authentication.Service/\">" +
          "<request xmlns:a=\"http://schemas.datacontract.org/2004/07/Ama.Structures.CCMovelSignature\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">" +
          "<a:ApplicationId>" + APPLICATIONID + "</a:ApplicationId>" +
          "<a:DocName>" + docName + "</a:DocName>" +
          "<a:Hash>" + docHash + "</a:Hash>" +
          "<a:Pin>" + pin + "</a:Pin>" +
          "<a:UserId>" + userId + "</a:UserId>" +
          "</request>" +
          "</CCMovelSign>" +
          "</soapenv:Body>" +
          "</soapenv:Envelope>"

        val url = new URL(stringUrl)
        val conn = url.openConnection.asInstanceOf[HttpURLConnection]
        conn.setRequestMethod("POST")
        conn.setDoOutput(true)
        conn.setDefaultUseCaches(false)
        conn.setRequestProperty("Content-type", "text/xml")
        conn.setRequestProperty("SOAPAction", SOAP_ACTION)

        //push the request to the server address
        val wr = new OutputStreamWriter(conn.getOutputStream)
        wr.write(body)
        wr.flush()

        //get the server response
        val reader = new BufferedReader(new InputStreamReader(conn.getInputStream))

        val builder = new StringBuilder
        var line = ""
        while ({line != null}) {
          line = reader.readLine()
          if (line != null) {
            builder.append(line)
            response = builder.toString
          }
        }
      } catch {
        case e: Exception =>
          logger.severe(s"Error $e")
          response = "Error"
      }
      response
    }

    private def parseResponse(response: String) = {
      val splited = response.split("</?a:ProcessId>")
      splited(1)
    }
  }

  class CMDValidateOtp(var otp: String, var processId: String, var applicationId: String = APPLICATIONID) {
    private val SOAP_ACTION = "http://Ama.Authentication.Service/CCMovelSignature/ValidateOtp"
    private val stringUrl = "https://cmd.autenticacao.gov.pt/Ama.Authentication.Frontend/CCMovelDigitalSignature.svc"

    def getApplicationId: String = applicationId

    def getOtp: String = otp

    def getProcessId: String = processId

    def ValidateOTP: String = {
      val response = sendSoapRequest
      if (response == "Error") die(msg = "Error running ValidateOTP service", args = "Error running ValidateOTP service")
      val signature = parseResponse(response)
      signature
    }

    private def parseResponse(response: String) = {
      val parsed = response.split("</?a:Signature>")
      val signature = parsed(1)
      signature
    }

    private def sendSoapRequest = {
      var response = ""
      //TO DO
      try {
        val body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ama=\"http://Ama.Authentication.Service/\">" +
          "<soapenv:Body>" +
          "<ama:ValidateOtp>" +
          "<ama:code>" + otp + "</ama:code>" +
          "<ama:processId>" + processId + "</ama:processId>" +
          "<ama:applicationId>" + APPLICATIONID + "</ama:applicationId>" +
          "</ama:ValidateOtp>" +
          "</soapenv:Body>" +
          "</soapenv:Envelope>"

        val url = new URL(stringUrl)
        val conn = url.openConnection.asInstanceOf[HttpURLConnection]
        conn.setRequestMethod("POST")
        conn.setDoOutput(true)
        conn.setDefaultUseCaches(false)
        conn.setRequestProperty("Content-type", "text/xml")
        conn.setRequestProperty("SOAPAction", SOAP_ACTION)

        //push the request to the server address
        val wr = new OutputStreamWriter(conn.getOutputStream)
        wr.write(body)
        wr.flush()

        //get the server response
        val reader = new BufferedReader(new InputStreamReader(conn.getInputStream))
        val builder = new StringBuilder
        var line = ""
        while ({line != null}) {
          line = reader.readLine()
          if (line != null) {
            builder.append(line)
            response = builder.toString
          }
        }
      } catch {
        case e: Exception =>
          logger.severe(s"Error $e")
          response = "Error"
      }
      response
    }
  }

  def checkTestAll(u: Boolean, d: Boolean): Boolean = {
    if (u && d)
      return true
    false
  }

  def checkGetCert(g: Boolean, a: Int, u: Boolean, c: Boolean, v: Boolean): Boolean = {
    if (g && a <= 5 && u && !c && !v)
      return true
    false
  }

  def checkCCMovel(c: Boolean, u: Boolean, d: Boolean, p: Boolean, g: Boolean, v: Boolean): Boolean = {
    if (c && u && d && p && !g && !v)
      return true
    false
  }

  def checkValidOTP(v: Boolean, a: Int, o: Boolean, p: Boolean, g: Boolean, c: Boolean): Boolean = {
    if (v && a <= 7 && o && p && !g && !c)
      return true
    false
  }

  def pemFile(userId: String, certs: List[String]): Unit = {

    try {
      val certPem = new FileOutputStream(s"$userId.pem")
      for (l <- certs) {
        val lbytes = (l + "\n").getBytes
        certPem.write(lbytes, 0, l.length + 1)
      }

      certPem.close()

    } catch {
      case e: IOException =>
        val msg = "Unable to create PEM file!"
        logger.severe(s"Error $e")
        println(msg)

    }
  }

  def getCertChain(userId: String) = {
    var aliasName = ""
    var nCert = 0
    val pem = new FileInputStream(s"$userId.pem")
    val contentPemFile = new BufferedInputStream(pem)
    val cf = CertificateFactory.getInstance("X.509")
    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType)
    keyStore.load(null)

    while ({contentPemFile.available > 0}) {
      if (nCert == 0) aliasName = "user"
      else if (nCert == 1) aliasName = "root"
      else if (nCert == 2) aliasName = "CA"
      val cert = cf.generateCertificate(contentPemFile)
      keyStore.setCertificateEntry(aliasName, cert)
      nCert += 1
    }

    keyStore

  }

  def getCn(chain: KeyStore, alias: String): String = {
    val cert = chain.getCertificate(alias).toString
    val temp = cert.split("Subject: CN=")(1).split(",")
    temp(0)
  }

  def testAll(userId: String, docName: String) = {
    println("\n+++ Test All inicializado +++\n")
    println(s"0% ... Leitura de argumentos da linha de comando ficheiro: '$docName', user: $userId ")
    println("10% ... A contactar servidor SOAP CMD para operação GetCertificate")

    val gc = new CMDGetCertificate(userId, applicationID)
    val certs = gc.getCertificate

    // geração do ficheiro pem
    pemFile(userId, certs.toList)

    val chain = getCertChain(userId)
    val nameUser = getCn(chain, "user")
    val nameRoot = getCn(chain, "root")
    val nameCa = getCn(chain, "CA")


    println(s"20% ... Certificado emitido para '$nameUser' pela Entidade de Certificação '$nameRoot' na hierarquia do '$nameCa'")
    println(s"30% ... Leitura do ficheiro '$docName'")

    val digests = MessageDigest.getInstance("SHA-256")
    try {
      val is = Files.newInputStream(Paths.get(docName))
      val dis = new DigestInputStream(is, digests)
      try {
      } finally {
        if (is != null) is.close()
        if (dis != null) dis.close()
      }
    } catch {
      case e: Exception =>
        logger.severe(s"Error $e")
        die(s"Unable to open the file $docName", s"Unable to open the file $docName")
    }
    println(s"40% ... Geração de hash do ficheiro '$docName'")

    val digest = digests.digest
    val preDigest = hashPrefix(digest)
    val digestNoPrefix = Base64.getEncoder.encodeToString(digest)
    val hashWithPrefix = Base64.getEncoder.encodeToString(preDigest)

    print(s"50% ... Hash gerada: $hashWithPrefix")

    println("\nIntroduza o pin:")
    val pin = scala.io.StdIn.readLine()
    if (!(pin forall Character.isDigit) && pin.length > 8)
      die("Pin not valid", "Pin not valid")

    println("60% ... A contactar servidor SOAP CMD para operação CCMovelSign")

    val mv = new CMDccMovelSign(userId, pin, docName, hashWithPrefix, applicationID)
    val proc = mv.ccMovelSign

    print(s"70% ... ProcessID devolvido pela operação CCMovelSign: $proc")

    println("\n80% ... A iniciar operação ValidateOtp\n")

    println("Introduza o OTP recebido no seu dispositivo:")
    val otp = scala.io.StdIn.readLine()
    if (!(otp forall Character.isDigit) && otp.length != 6)
      die("OTP not valid", "OTP not valid")

    println("90% ... A contactar servidor SOAP CMD para operação ValidateOtp")

    val vl = new CMDValidateOtp(otp, proc, applicationID)
    val signature = vl.ValidateOTP

    print(s"100% ... Assinatura devolvida pela operação ValidateOtp: $signature")

    print("\n110% ... A validar assinatura ...\n")

    val pubkey = chain.getCertificate("user").getPublicKey

    val isCorrect = verify(digestNoPrefix, signature, pubkey)
    println("Assinatura verificada? " + isCorrect)

    "\n+++ Test All finalizado +++\n"
  }

  def verify(plainText: String, signature: String, publicKey: PublicKey) = {

    val publicSignature = Signature.getInstance("SHA256withRSA")
    publicSignature.initVerify(publicKey)
    publicSignature.update(plainText.getBytes(StandardCharsets.UTF_8))
    val signatureBytes = Base64.getDecoder.decode(signature)
    publicSignature.verify(signatureBytes)
  }

  // Devolve a hash acrescentada do prefixo do tipo de hash utilizada

  def hashPrefix(hash: Array[Byte]) = {
    val Sha256 = Array[Byte](0x30.toByte, 0x31.toByte, 0x30.toByte, 0x0d.toByte, 0x06.toByte, 0x09.toByte,
      0x60.toByte, 0x86.toByte, 0x48.toByte, 0x01.toByte, 0x65.toByte, 0x03.toByte,
      0x04.toByte, 0x02.toByte, 0x01.toByte, 0x05.toByte, 0x00.toByte, 0x04.toByte, 0x20.toByte)

    val hashWithPrefix = Sha256 ++ hash
    hashWithPrefix
  }

  def hash(msg: String) = {
    val digest = MessageDigest.getInstance("SHA-256")
    digest.digest(msg.getBytes)
  }

  // ArgParse
  val usage =
    """
  Usage: CMDGetCertificate.scala <oper> options ...
  example: GetCertificate -u userID  [-a applicationID]
           CCMovelSign -u userID [-a applicationID] -s pin -d docName [-H hash]
           ValidateOtp [-a applicationID] -o OTP -p processID
           TestAll -u userID -d docName
  """

  var userID: String = ""
  var applicationID: String = ""
  var pin: String = ""
  var processID: String = ""
  var otp: String = ""
  var docName: String = ""
  var docHash: String = ""
  val unknown = "(^-[^\\s])".r
  var getcert = false
  var validotp = false
  var ccmovel = false
  var help = false
  var testAll = false

  val pf: PartialFunction[List[String], List[String]] = {
    case "GetCertificate" :: tail => getcert = true; tail
    case "CCMovelSign" :: tail => ccmovel = true; tail
    case "ValidateOtp" :: tail => validotp = true; tail
    case "TestAll" :: tail => testAll = true; tail
    case "-h" :: tail => help = true; tail
    case "-u" :: (arg: String) :: tail => userID = arg; tail
    case "-a" :: (arg: String) :: tail => applicationID = arg; tail
    case "-s" :: (arg: String) :: tail => pin = arg; tail
    case "-p" :: (arg: String) :: tail => processID = arg; tail
    case "-o" :: (arg: String) :: tail => otp = arg; tail
    case "-d" :: (arg: String) :: tail => docName = arg; tail
    case "-H" :: (arg: String) :: tail => docHash = arg; tail
    case unknown(bad) :: tail => die("unknown argument " + bad + "\n" + usage, args = s"Invalid parameters: $bad")
  }

  if (help) die(args = "")

  def cmd(args: Array[String]) {

    // Validate args length
    if (args.length == 0 || args.length >= 11) die(args = "Invalid parameters.")
    val arglist = args.toList
    for (a <- arglist)
      if (a.length > 60) die(args = "Invalid parameters.")

    val remainingopts = parseArgs(arglist, pf)

    if (checkGetCert(getcert, args.length, !userID.isEmpty, ccmovel, validotp)) {
      println("Get Certificate")

      logger.info(s"Parameters: operation: GetCertificate, userID: $userID, applicationID: $applicationID")

      val op = new CMDGetCertificate(userID, applicationID)
      val certs = op.getCertificate
      // Create PEM file
      pemFile(userID, certs.toList)
      println("Certificates: ")
      for (cert <- certs.toList)
        println(cert)
    }

    else if (checkCCMovel(ccmovel, !userID.isEmpty, !docName.isEmpty, !pin.isEmpty, getcert, validotp)) {
      println("CCMovelSign")

      logger.info(s"Parameters: operation: CCMovelSign, userID: $userID, applicationID: $applicationID, docName: $docName")

      // Hash do Documento

      val docHashBytes = docHash.getBytes()
      if (docHash.isEmpty) {
        val message = "Nobody inspects the spammish repetition"
        val encodedHash = hash(message)
        val hashWithPrefix = hashPrefix(encodedHash)
        docHash = Base64.getEncoder.encodeToString(hashWithPrefix)
      }
      else {
        val hashWithPrefix = hashPrefix(docHashBytes)
        docHash = Base64.getEncoder.encodeToString(hashWithPrefix)
      }

      val op = new CMDccMovelSign(userID, pin, docName, docHash, applicationID)
      val proc = op.ccMovelSign

      println("ProcessID: " + proc)
    }

    else if (checkValidOTP(validotp, args.length, !otp.isEmpty, !processID.isEmpty, getcert, ccmovel)) {
      println("ValidateOTP")

      logger.info(s"Parameters: operation: ValidateOtp, processID: $processID, applicationID: $applicationID")

      val op = new CMDValidateOtp(otp, processID, applicationID)
      val sig = op.ValidateOTP
      println(sig)
    }

    else if (checkTestAll(!userID.isEmpty, !docName.isEmpty)) {
      logger.info(s"TestAll for user: $userID and document: $docName")
      testAll(userID, docName)
    }

    else die(args = s"Invalid parameters: $arglist")

  }

  def parseArgs(args: List[String], pf: PartialFunction[List[String], List[String]]): List[String] = args match {
    case Nil => Nil
    case _ => if (pf isDefinedAt args) parseArgs(pf(args), pf) else args.head :: parseArgs(args.tail, pf)
  }

  def die(msg: String = usage, args: String) = {
    logger.severe(args)
    println(msg)
    sys.exit(1)
  }

  Main.cmd(args)

}