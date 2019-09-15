# distributedSystemsTaskSystem
Sample application with Image media distributed task system-> Demo ntier architecture

The application consists of the following servers:
Web server (using nginx)
Application server (using tomcat [embedded in spring boot])
DB server (using postgres)
Session server (using redis for keeping application server's session)
Network Storage (Using PVC Dynamic provisioning or nfs)
