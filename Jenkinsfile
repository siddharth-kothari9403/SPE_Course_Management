// pipeline {
//     environment {
//         DOCKERHUB_CRED = credentials("Dockerhub-Credentials-ID")
//     }
//     agent any
//     stages {
//         stage("Stage 1: Maven Build") {
//             steps {
//                 // Build the custom MySQL image
//                 dir('sql') {
//                     sh 'docker build -t siddharthkothari9403/mysql:latest .'
//                 }

//                 // Run the MySQL container
//                 sh 'docker run -d --rm --name test-db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=elective_management -p 3306:3306 siddharthkothari9403/mysql:latest'

//                 // Wait for the database to be ready
//                 sh '''
//                     for i in {1..10}; do
//                         if docker exec test-db mysqladmin ping -uroot -proot | grep -q "mysqld is alive"; then
//                             echo "Database is ready!";
//                             break;
//                         fi
//                         echo "Waiting for database to be ready...";
//                         sleep 5;
//                     done
//                 '''

//                 // Run Maven build
//                 dir('ElectiveManagement') {
//                     sh 'mvn clean install'
//                 }

//                 // Stop the MySQL container
//                 sh 'docker stop test-db'
//             }
//         }
//         stage("Stage 2: Build Docker Images") {
//             parallel {
//                 stage("Build Frontend Image") {
//                     steps {
//                         dir('elecfr-web') {
//                             sh 'docker build -t siddharthkothari9403/elecfr-web:latest .'
//                         }
//                     }
//                 }
//                 stage("Build Backend Image") {
//                     steps {
//                         dir('ElectiveManagement') {
//                             sh 'docker build -t siddharthkothari9403/elective-management:latest .'
//                         }
//                     }
//                 }
//                 stage("Build Database Image") {
//                     steps {
//                         dir('sql') {
//                             sh 'docker build -t siddharthkothari9403/mysql:latest .'
//                         }
//                     }
//                 }
//             }
//         }
//         stage("Stage 3: Push Docker Images to Dockerhub") {
//             steps {
//                 sh 'echo $DOCKERHUB_CRED_PSW | docker login -u $DOCKERHUB_CRED_USR --password-stdin'
//                 sh 'docker push siddharthkothari9403/elecfr-web:latest'
//                 sh 'docker push siddharthkothari9403/elective-management:latest'
//                 sh 'docker push siddharthkothari9403/mysql:latest'
//             }
//         }
//         stage("Stage 4: Ansible Deployment") {
//             steps {
//                 dir('deploy') {
//                     sh 'ansible-playbook -i inventory deploy-k8s.yml'
//                 }
//             }
//         }
//     }
// }

pipeline {
    agent any
    environment {
        DOCKER_CREDENTIALS_ID = 'Dockerhub-Credentials-ID' // Jenkins credentials ID for Docker Hub
        DOCKER_HUB_REPO = 'siddharthkothari9403' // Docker Hub username or repo name
    }
    stages {
        stage('Clone Code') {
            steps {
                // Clone the repository
                git 'https://github.com/siddharth-kothari9403/SPE_Course_Management.git' 
            }
        }
        stage('Build and Tag Images') {
            steps {
                script {
                    // Use Docker Compose to build images
                    sh """
                        docker-compose -f docker-compose.yaml build \
                        --build-arg DOCKER_HUB_REPO=$DOCKER_HUB_REPO
                    """
                }
            }
        }
        stage('Push to Docker Hub') {
            steps {
                script {
                    // Login to Docker Hub and push images using docker-compose
                    docker.withRegistry('', DOCKER_CREDENTIALS_ID) {
                        sh """
                           docker-compose -f docker-compose.yaml push
                        """
                    }
                }
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
