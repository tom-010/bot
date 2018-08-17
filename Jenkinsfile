pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh './gradlew test'
                sh './gradlew cucumber'

            }
        }
        stage('Test-Coverage') {
            steps {
                sh './gradlew jacocoTestCoverageVerification'
            }
        }
        stage('Build') {
            steps {
               sh './gradlew build'
            }
        }
        stage('Dockerize') {
            steps {
                sh 'echo "tdeniffelbot" | docker login --username tdeniffelbot --password-stdin'
                sh 'docker build . -t tdeniffelbot/bot'
                sh 'docker push tdeniffelbot/bot'
            }
        }
    }
    post {
        failure {
            emailext (
                subject: "Bot auf Rot",
                body: """<p>ACHTUNG!</p>
                <p style="color: red"><strong>Das Bot-Projekt ist nicht stabil!</strong></p>
               <p>VG,</p>
                Thomas""",
                to: "tdeniffel@gmail.com"
            )
        }
    }
}
