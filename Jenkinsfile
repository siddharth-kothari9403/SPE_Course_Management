pipeline {
    environment {
        DOCKERHUB_CRED = credentials("Dockerhub-Credentials-ID")
    }
    agent any
    stages {
        stage("Stage 1: Maven Build") {
            steps {
                dir('ElectiveManagement') {
                    sh 'mvn clean install'
                }
            }
        }
        
        stage("Stage 2: Build Docker Images") {
            parallel {
                stage("Build Frontend Image") {
                    steps {
                        dir('elecfr-web') {
                            sh 'docker build -t sankalpkothari/elecfr-web:latest .'
                        }
                    }
                }
                stage("Build Backend Image") {
                    steps {
                        dir('ElectiveManagement') {
                            sh 'docker build -t sankalpkothari/elective-management:latest .'
                        }
                    }
                }
                stage("Build Database Image") {
                    steps {
                        dir('sql') {
                            sh 'docker build -t sankalpkothari/mysql:latest .'
                        }
                    }
                }
            }
        }
        
        stage("Stage 3: Push Docker Images to Dockerhub") {
            steps {
                sh 'echo $DOCKERHUB_CRED_PSW | docker login -u $DOCKERHUB_CRED_USR --password-stdin'
                sh 'docker push sankalpkothari/elecfr-web:latest'
                sh 'docker push sankalpkothari/elective-management:latest'
                sh 'docker push sankalpkothari/mysql:latest'
            }
        }
        
        stage("Stage 4: Ansible Deployment") {
            steps {
                dir('deploy') {
                    sh 'ansible-playbook -i inventory deploy-app.yml'
                }
            }
        }
    }
}