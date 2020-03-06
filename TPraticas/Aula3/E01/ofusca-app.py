# coding: latin-1
###############################################################################
# eVotUM - Electronic Voting System
#
# ofusca-app.py
#
# Cripto-7.1.1 - Commmad line app to exemplify the usage of blindData
#       function (see eccblind.py)
#
# Copyright (c) 2016 Universidade do Minho
# Developed by André Baptista - Devise Futures, Lda. (andre.baptista@devisefutures.com)
# Reviewed by Ricardo Barroso - Devise Futures, Lda. (ricardo.barroso@devisefutures.com)
#
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
#
###############################################################################
"""
Command line app that receives Data and pRDashComponents from STDIN and writes
blindMessage and blindComponents and pRComponents to STDOUT.
"""

import sys
from eVotUM.Cripto import eccblind


def printUsage():
    print("Usage: python ofusca-app.py -msg <mensagem a assinar> -RDash <pRDashComponents>")


def parseArgs():
    if len(sys.argv) != 5:
        printUsage()
    else:
        data = sys.argv[2]
        RDash = sys.argv[4]
        main(data, RDash)


def showResults(errorCode, result):
    print("Output")
    if errorCode is None:
        blindComponents, pRComponents, blindM = result
        print("Blind message: %s" % blindM)

        with open('requesterFile.txt', 'w') as f:
            bc = 'Blind components: ' + blindComponents + '\n'
            prc = 'pRComponents: ' + pRComponents + '\n'
            f.write(bc)
            f.write(prc)
            print('Blind components and pRComponents stored in requesterFile.txt.')
    elif errorCode == 1:
        print("Error: pRDash components are invalid")


def main(data, pRDashComponents):
    errorCode, result = eccblind.blindData(pRDashComponents, data)
    showResults(errorCode, result)


if __name__ == "__main__":
    parseArgs()
