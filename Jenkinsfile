pipeline {
    agent any

    environment {
        IMAGE_NAME = 'trackmyspend'
        CONTAINER_NAME = 'trackmyspend-container'
    }

    stages {
        stage('Cloner le code') {
            steps {
                git 'https://github.com/jeremy-Ablrd/Track-My-Spend.git'
            }
        }

        stage('Installation des dépendances') {
            steps {
                script {
                    sh './mvnw clean install -DskipTests'
                }
            }
        }

        stage('Tests') {
            steps {
                script {
                    sh './mvnw test'
                }
            }
        }

        stage('Build du projet') {
            steps {
                script {
                    sh './mvnw package -DskipTests'
                }
            }
        }

        // stage('Déploiement') {
        //     steps {
        //         script {
        //             echo "Déploiement en cours..."
        //             // Déploiement vers un serveur ou un container Docker
        //             // Ex : scp vers un serveur ou un docker run
        //             sh 'docker run -d --name $CONTAINER_NAME -p 8080:8080 target/trackmyspend.jar'
        //         }
        //     }
        // }
    }
}
