"""
Example topology of Quagga routers
"""

import inspect
import os
from mininext.topo import Topo
from mininext.services.quagga import QuaggaService

from collections import namedtuple

QuaggaHost = namedtuple("QuaggaHost", "name ip loIP")
net = None


class QuaggaTopo(Topo):

    "Creates a topology of Quagga routers"

    def __init__(self):
        """Initialize a Quagga topology with 5 routers, configure their IP
           addresses, loop back interfaces, and paths to their private
           configuration directories."""
        Topo.__init__(self)

        # Directory where this file / script is located"
        selfPath = os.path.dirname(os.path.abspath(
            inspect.getfile(inspect.currentframe())))  # script directory

        # Initialize a service helper for Quagga with default options
        quaggaSvc = QuaggaService(autoStop=False)

        # Path configurations for mounts
        quaggaBaseConfigPath = selfPath + '/configs/'

        # List of Quagga host configs
        quaggaHosts = []
        quaggaHosts.append(QuaggaHost(name='h1', ip='50.1.1.1/24',loIP=None))
        quaggaHosts.append(QuaggaHost(name='r1', ip='50.1.1.2/24',loIP=None))
        quaggaHosts.append(QuaggaHost(name='r2', ip='51.1.1.2/24',loIP=None))
        quaggaHosts.append(QuaggaHost(name='r3', ip='52.1.1.2/24',loIP=None))
        quaggaHosts.append(QuaggaHost(name='r4', ip='55.7.7.2/24',loIP=None))
        quaggaHosts.append(QuaggaHost(name='h2', ip='55.7.7.1/24',loIP=None))

        # Add switch for IXP fabric
        #ixpfabric = self.addSwitch('fabric-sw1')
        quaggaList = []
        # Setup each Quagga router, add a link between it and the IXP fabric
        for host in quaggaHosts:

            # Create an instance of a host, called a quaggaContainer
            quaggaContainer = self.addHost(name=host.name,
                                           ip=host.ip,
                                           hostname=host.name,
                                           privateLogDir=True,
                                           privateRunDir=True,
                                           inMountNamespace=True,
                                           inPIDNamespace=True,
                                           inUTSNamespace=True)
            quaggaList.append(quaggaContainer)
            # Add a loopback interface with an IP in router's announced range
            self.addNodeLoopbackIntf(node=host.name, ip=host.loIP)

            # Configure and setup the Quagga service for this node
            quaggaSvcConfig ={'quaggaConfigPath': quaggaBaseConfigPath + host.name}
            self.addNodeService(node=host.name, service=quaggaSvc,nodeConfig=quaggaSvcConfig)

            # Attach the quaggaContainer to the IXP Fabric Switch
            #self.addLink(quaggaContainer, ixpfabric)
        self.addLink(quaggaList[0], quaggaList[1])
        self.addLink(quaggaList[1], quaggaList[2])
        self.addLink(quaggaList[1], quaggaList[3])
        self.addLink(quaggaList[5], quaggaList[4])
        self.addLink(quaggaList[2], quaggaList[4])
        self.addLink(quaggaList[3], quaggaList[4])
