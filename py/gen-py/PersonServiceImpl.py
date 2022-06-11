from py.thrift.generated import ttypes


class PersonServiceImpl:

    def getPersonByUsername(self, username):
        print(username)

        person = ttypes.Person()
        person.username = username
        person.age = 20
        person.married = False

        return person

    def savePerson(self, person):
        print('savePerson')

        print(person.username)
        print(person.age)
        print(person.married)
