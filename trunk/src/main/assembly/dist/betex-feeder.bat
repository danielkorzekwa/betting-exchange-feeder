rem set DEBUG=-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n
rem set JMX=-Dcom.sun.management.jmxremote.port=20000 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
java %JMX% %DEBUG% -Xmx512m -cp ./;./lib/betex-server-${project.version}.jar dk.betex.feeder.BetexFeederApp %*