
from py.thrift.generated import PersonService
from py.thrift.generated import ttypes

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol

try:
    t_socket = TSocket.TSocket('localhost', 8899)
    t_socket.setTimeout(600)

    transport = TTransport.TFramedTransport(t_socket)
    protocol = TCompactProtocol.TCompactProtocol(transport)
    client = PersonService.Client(protocol)

    transport.open()

    person = client.getPersonByUsername('zhang san中国')
    print(person.username)
    print(person.age)
    print(person.married)

    new_person = ttypes.Person()
    new_person.username = 'will'
    new_person.age = 30
    new_person.married = True

    client.savePerson(new_person)

    transport.close()
except Thrift.TException as tx:
    print(tx)
