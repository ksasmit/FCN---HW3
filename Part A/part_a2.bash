h1 ip route add 55.7.7.0/24 via 50.1.1.2 dev h1-eth0
h1 ip route add 52.1.1.0/24 via 50.1.1.2 dev h1-eth0
r1 ip route add 55.7.7.0/24 via 51.1.1.2 dev r1-eth1
r1 ip route add 55.7.7.0/24 via 52.1.1.2 dev r1-eth2
r2 ip route add 55.7.7.0/24 via 53.1.1.1 dev r2-eth1
r2 ip route add 50.1.1.0/24 via 51.1.1.1 dev r2-eth0
r3 ip route add 55.7.7.0/24 via 54.1.1.1 dev r3-eth1
r3 ip route add 50.1.1.0/24 via 52.1.1.1 dev r3-eth0
r4 ip route add 55.7.7.0/24 via 55.7.7.2 dev r4-eth0
r4 ip route add 50.1.1.0/24 via 54.1.1.2 dev r4-eth2
h2 ip route add 50.1.1.0/24 via 55.7.7.2 dev h2-eth0
h2 ip route add 53.1.1.0/24 via 55.7.7.2 dev h2-eth0
h2 ip route add 54.1.1.0/24 via 55.7.7.2 dev h2-eth0

r1 iptables -t nat -A POSTROUTING -o r1-eth0 -j MASQUERADE
r1 iptables -A FORWARD -i r1-eth0 -o r1-eth1 -m state --state RELATED,ESTABLISHED -j ACCEPT
r1 iptables -A FORWARD -i r1-eth0 -o r1-eth1 -j ACCEPT

r2 iptables -t nat -A POSTROUTING -o r2-eth1 -j MASQUERADE
r2 iptables -A FORWARD -i r2-eth0 -o r2-eth1 -m state --state RELATED,ESTABLISHED -j ACCEPT
r2 iptables -A FORWARD -i r2-eth0 -o r2-eth1 -j ACCEPT

r4 iptables -t nat -A POSTROUTING -o r4-eth1 -j MASQUERADE
r4 iptables -A FORWARD -i r4-eth1 -o r4-eth0 -m state --state RELATED,ESTABLISHED -j ACCEPT
r4 iptables -A FORWARD -i r4-eth1 -o r4-eth0 -j ACCEPT


r4 iptables -A FORWARD -i r4-eth0 -o r4-eth2 -j ACCEPT
r4 iptables -A FORWARD -i r4-eth0 -o r4-eth2 -m state --state RELATED,ESTABLISHED -j ACCEPT
r4 iptables -t nat -A POSTROUTING -o r4-eth2 -j MASQUERADE

r3 iptables -A FORWARD -i r3-eth1 -o r3-eth0 -j ACCEPT
r3 iptables -A FORWARD -i r3-eth1 -o r3-eth0 -m state --state RELATED,ESTABLISHED -j ACCEPT
r3 iptables -t nat -A POSTROUTING -o r3-eth0 -j MASQUERADE
