#Never Build a Custom JSON Serializer
A perfect example why you should (probably) never write a custom JSON Serializer for your Java Application

build dist

Unzip all jars into Tomcat / webapps / ROOT / WEB-INF / lib
Unzip web.xml into Tomcat / webapps / ROOT / WEB-INF

Restart Tomcat

Browse http://localhost:8080/string?iterations=10000&serializer=custom

==> See how the server respond after 113334 milliseconds


Browse http://localhost:8080/string?iterations=10000&serializer=fast

==> See how the server respond after 240 milliseconds


No possible comparisons!