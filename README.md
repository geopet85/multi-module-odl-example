# Multi-module Opendaylight Example
This is a multi-module Opendaylight example, performing some simple operations with the SDN Controller. 

It exposes an API to add applications, uses the registry to store them, configures flow rules, and does link monitoring and topology functions, and topology change listener, using separate modules and different types of bundles and interfaces. 

## Prerequisites
To build the project, you have to setup Java and Maven first, according to the [instructions](https://wiki.opendaylight.org/view/GettingStarted:Development_Environment_Setup).

##Build & Run
<code>mvn clean install -DskipTests</code>

<code>cd karaf/target/assembly/bin</code>

<code>./karaf</code>

To check that our modules are running, use the following commands in the Opendaylight console:

<code>bundle:list | grep 1.0.0.SNAPSHOT</code>  (All project bundles should be active)

##Basic Example
Start the Opendaylight with the previous commands.

Install Mininet and run a simple topology, e.g. 1 switch, 2 hosts, by executing <code>sudo mn --controller remote,ip="sdn-controller-ip" --topo single, 2</code>

Browse the [ODL user interface](http://localhost:8181/index.html) and connect with admin/admin credentials.

Browse the [Yang UI page](http://localhost:8181/index.html#/yangui/index), expand all in API tab and find *reference* and *negotiator* APIs.

In the Mininet console, execute <code>pingall</code>. Then in the Opendaylight console, execute <code>log:display | grep eu.virtuwind</code> and you should see the topology change notification update. 

In *reference* bullet, add an application. Then in *operational* bullet, check whether it has been stored in the registry.

In *negotiator* bullet, send the negotiation requirements. In the same page, you will also receive a response.

In the Opendaylight console, execute <code>log:display</code> to check that certain functions work properly when you use the REST API.

In the Mininet console, execute <code>sh ovs-ofctl dump-flows s1</code> to check that a new flow rule is inserted.
