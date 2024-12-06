pipeline {
    
    environment {
        DOCKERHUB_CRED = credentials("Dockerhub-Credentials-ID") // Jenkins credentials ID for Docker Hub
        DOCKER_HUB_REPO = 'siddharthkothari9403' // Docker Hub username or repo name
    }

    agent any
    stages {

        stage('Build and Tag Images') {
            steps {

                dir('sql') {
                    sh "docker build -t ${DOCKER_HUB_REPO}/mysql:latest ."
                }

                dir('ElectiveManagement') {
                    sh "docker build -t ${DOCKER_HUB_REPO}/elective-management:latest ."
                }

                dir('elecfr-web') {
                    sh "docker build -t ${DOCKER_HUB_REPO}/elecfr-web:latest ."
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                sh 'echo $DOCKERHUB_CRED_PSW | docker login -u $DOCKERHUB_CRED_USR --password-stdin'
                sh "docker push ${DOCKER_HUB_REPO}/elecfr-web:latest"
                sh "docker push ${DOCKER_HUB_REPO}/elective-management:latest"
                sh "docker push ${DOCKER_HUB_REPO}/mysql:latest"
            }
        }

        stage('Clean Local Docker Images') {
            steps {
                sh "docker rmi ${DOCKER_HUB_REPO}/elecfr-web:latest || true"
                sh "docker rmi ${DOCKER_HUB_REPO}/elective-management:latest || true"
                sh "docker rmi ${DOCKER_HUB_REPO}/mysql:latest || true"
            }
        }

        stage('Deploy with Docker Compose and Ansible') {
            steps {
                script {
                    // Optionally, use Ansible for deployment
                    ansiblePlaybook(
                        playbook: 'deploy-app.yaml',
                        inventory: 'inventory',
                        // credentialsId: 'ansible-ssh-credentials' // Jenkins SSH credentials ID
                    )
                }
            }
        }
        
        stage('Verify Workspace') {
            steps {
                sh 'ls -R'  // List all files in the workspace recursively
            }
        }
    }
}
