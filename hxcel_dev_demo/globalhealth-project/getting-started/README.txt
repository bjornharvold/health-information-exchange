This will guide you through getting Globalhealth up and running in your development environment.

Java
1. JCE
	- To install the JCE follow the readme in the jce sub-directory 
2. BouncyCastle Provider
	- After installing the JCE, you have to configure BouncyCastle as a JCE provider. Open java.security under <jdk_home>/jre/lib/security and add the line: 
	security.provider.10=org.bouncycastle.jce.provider.BouncyCastleProvider
	under the list of providers. The number 10 is just the number incremented from the last provider listed.
3. Set an environment variable for your OS called: APP_ENCRYPTION_PASSWORD, with the value: suchNunh0ly!...c0nf3d3r3at10n%$
4. Database: Install PostgreSQL v8.3.x +
	- Create a database called globalhealth_db
	- Create a new role called: inl1f3, with password: becKy!45
	- Make sure you enable TCP/IP connections by editing postgres.conf located under <PGSQL_HOME>/data
5. Install Maven 2
	- In the root directory (hxcel-parent.pom) run: mvn to make sure the project compiles
6. Install Tomcat
5. Open Eclipse or IntelliJ and import the HXCEL project from the hxcel-parent.pom file.

You can run a smaller version of the app if you are just going to work with Flex. The globalhealth-domain module contains a JettyServer executable class that you can run to start up a servlet runner with all web services. You can then start up Flex and open the globalhealth-flex project. This will be enough for almost all cases.

Flex
1. Open the flex project located under globalhealth-flex
2. If you have not already done so, associate all external libs from the libs directory. This should have already been done for you but you never know.
3. Make sure you are also referencing flex-commons. Open the flex-commons project and link it as a project dependency in globalhealth-flex. That way if you have to make changes to flex-commons, it will immediately reflect in globalhealth-flex without having to copy a new library over to the libs directory.