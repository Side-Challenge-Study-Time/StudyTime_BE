pipeline {
    agent any
    stages {
       stage('Prepare') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/Side-Challenge-Study-Time/StudyTime_BE.git'
            }
        }
		stage('Build Gradle') {
            steps{
                sh 'chmod +x gradlew'
                sh  './gradlew clean build'

                sh 'ls -al ./build'
            }
        }

		stage('Docker build image') {
            steps{
                sh 'docker build -t kimmugeon/docker-jenkins-github-test .'
            }
        }

		stage('Docker push image') {
            steps{
                sh 'docker login -u {id} -p {password}'
                sh 'docker push kimmugeon/docker-jenkins-github-test'
            }
        }

        stage('Run Container on SSH Dev Server'){
            steps{
                echo 'SSH'
                sshagent (credentials: ['dockerHubPwd']) {

					sh "ssh -o StrictHostKeyChecking=no ubuntu@13.209.xxx.xxx 'whoami'"
                    // sh "ssh -o StrictHostKeyChecking=no ubuntu@13.209.121.180 'docker ps -q --filter name=docker-jenkins-github-test | grep -q . && docker rm -f \$(docker ps -aq --filter name=docker-jenkins-github-test)'"
                    // sh "ssh -o StrictHostKeyChecking=no ubuntu@13.209.121.180 'docker rmi -f kimmugeon/docker-jenkins-github-test'"
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@13.209.xxx.xxx 'docker run -d --name docker-jenkins-github-test -p 8081:8080 kimmugeon/docker-jenkins-github-test'"

                }

            }

        }
    }
}