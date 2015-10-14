# Run project with maven
		mvn spring-boot:run

# Package project
		mvn package
		# package result: ./target/tamada-bayanist-0.0.1-SNAPSHOT.jar

# Run project as standalone (after package):
		java -jar ./target/tamada-bayanist-0.0.1-SNAPSHOT.jar

# Check rest:
		curl localhost:7777/currency/api/rate/USD
		curl localhost:7777/currency/api/rate/USD/2015-09-24
