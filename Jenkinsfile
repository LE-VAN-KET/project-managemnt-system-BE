pipeline{
    agent any

    stages{
        stage('Prepare workspace') {
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                echo 'Prepare workspace'
                // Clean workspace
                step([$class: 'WsCleanup'])
                // Checkout git
                script {
                    def commit = checkout scm
                    env.BRANCH_NAME = commit.GIT_BRANCH.replace('origin/', '')
                }
            }
        }

        stage('Dependencies'){
            agent {
                docker {
                    image 'maven:3-alpine'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                echo 'Dependency stage'
                script {
                    sh "echo 'Downloading dependencies...'"
                    sh "mvn -s settings.xml clean install -DskipTests=true"
                }
            }
        }

        stage('Testing') {
            steps {
                echo 'Test stage'
                script {
                    sh "echo 'JUnit testing...'"
                    sh "cd ./user-service && ./mvnw -s settings.xml test"
                    jacoco(execPattern: 'user-service/target/jacoco.exec')
                    sh "cd ./organization-service && ./mvnw -s settings.xml test"
                    jacoco(execPattern: 'organization-service/target/jacoco.exec')
                    sh "cd ./issues-service && ./mvnw -s settings.xml test"
                    jacoco(execPattern: 'issues-service/target/jacoco.exec')
                    sh "cd ./member-service && ./mvnw -s settings.xml test"
                    jacoco(execPattern: 'member-service/target/jacoco.exec')

                }
            }
        }

        stage('SonarQube User-service') {
            agent {
                docker {
                    image 'jenkins/jnlp-agent-maven:jdk11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            environment {
                scannerHome = tool 'SonarQube'
            }
            steps {
                withSonarQubeEnv(installationName: 'SonarQube') {
                    sh """cd ./user-service && ./mvnw -s settings.xml clean verify sonar:sonar -Dsonar.projectKey=user-service" \
                    -Dsonar.host.url=http://146.190.105.184:10000 -Dsonar.login=sqa_13efc056525ae8add04170822913d63831329f84
                    """
                }

                timeout(time: 60, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }

                script {
                    def sonar = waitForQualityGate()
                    if (sonar.status != 'OK') {
                        if (sonar.status == 'WARN') {
                            currentBuild.result = 'UNSTABLE'
                        } else {
                            error "Quality gate User-service is broken"
                        }
                    }
                }
            }
        }

        stage('SonarQube Organization-service') {
            agent {
                docker {
                    image 'jenkins/jnlp-agent-maven:jdk11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            environment {
                scannerHome = tool 'SonarQube'
            }
            steps {
                withSonarQubeEnv(installationName: 'SonarQube') {
                    sh """cd ./organization-service && ./mvnw -s settings.xml clean verify sonar:sonar -Dsonar.projectKey=organization-service" \
                    -Dsonar.host.url=http://146.190.105.184:10000 -Dsonar.login=sqa_13efc056525ae8add04170822913d63831329f84
                    """
                }

                timeout(time: 60, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }

                script {
                    def sonar = waitForQualityGate()
                    if (sonar.status != 'OK') {
                        if (sonar.status == 'WARN') {
                            currentBuild.result = 'UNSTABLE'
                        } else {
                            error "Quality gate Organization-service is broken"
                        }
                    }
                }
            }
        }

        stage('SonarQube Member-service') {
            agent {
                docker {
                    image 'jenkins/jnlp-agent-maven:jdk11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            environment {
                scannerHome = tool 'SonarQube'
            }
            steps {
                withSonarQubeEnv(installationName: 'SonarQube') {
                    sh """cd ./member-service && ./mvnw -s settings.xml clean verify sonar:sonar -Dsonar.projectKey=member-service" \
                    -Dsonar.host.url=http://146.190.105.184:10000 -Dsonar.login=sqa_13efc056525ae8add04170822913d63831329f84
                    """
                }

                timeout(time: 60, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }

                script {
                    def sonar = waitForQualityGate()
                    if (sonar.status != 'OK') {
                        if (sonar.status == 'WARN') {
                            currentBuild.result = 'UNSTABLE'
                        } else {
                            error "Quality gate Member-service is broken"
                        }
                    }
                }
            }
        }

        stage('SonarQube Issues-service') {
            agent {
                docker {
                    image 'jenkins/jnlp-agent-maven:jdk11'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            environment {
                scannerHome = tool 'SonarQube'
            }
            steps {
                withSonarQubeEnv(installationName: 'SonarQube') {
                    sh """cd ./issues-service && ./mvnw -s settings.xml clean verify sonar:sonar -Dsonar.projectKey=issues-service" \
                    -Dsonar.host.url=http://146.190.105.184:10000 -Dsonar.login=sqa_13efc056525ae8add04170822913d63831329f84
                    """
                }

                timeout(time: 60, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }

                script {
                    def sonar = waitForQualityGate()
                    if (sonar.status != 'OK') {
                        if (sonar.status == 'WARN') {
                            currentBuild.result = 'UNSTABLE'
                        } else {
                            error "Quality gate Issues-service is broken"
                        }
                    }
                }
            }
        }

        stage("Build Image And Deploy environment Dev"){
            when {
                expression {
                    return (env.BRANCH_NAME == 'origin/develop' | env.BRANCH_NAME == 'develop')
                }
            }
            steps{
                echo "========Build And Push image to test environment========"
                script {
                    sh "cd infrastructure/docker-compose && docker-compose -f common.yml -f service.yml build"
                    sh "docker login -u vanket -p dckr_pat_V1ZSZ0lJu4IESrxEJz_45ClFc60"
                    sh "docker tag user-service:v1.0.0 vanket/user-service:v1.0.0"
                    sh "docker tag member-service:v1.0.0 vanket/member-service:v1.0.0"
                    sh "docker tag organization-service:v1.0.0 vanket/organization-service:v1.0.0"
                    sh "docker tag issues-service:v1.0.0 vanket/issues-service:v1.0.0"
                    sh "docker push vanket/user-service:v1.0.0"
                    sh "docker push vanket/issues-service:v1.0.0"
                    sh "docker push vanket/member-service:v1.0.0"
                    sh "docker push vanket/organization-service:v1.0.0"

                    echo "remove all image"
                    sh """docker rmi vanket/issues-service:v1.0.0 vanket/member-service:v1.0.0 \
                    vanket/user-service:v1.0.0  vanket/organization-service:v1.0.0 user-service:v1.0.0 \
                    member-service:v1.0.0 organization-service:v1.0.0 issues-service:v1.0.0 -f
                    """

                    echo "Login into server restart container"
                }
            }
        }
    }
    post{
        always{
            echo "========always========"
        }
        aborted {
            echo "Sending message to Slack"
            slackSend (color: "${env.SLACK_COLOR_WARNING}",
                        channel: "#jenkins-notification",
                        message: "${custom_msg_notification('ABORTED')}")
        }
        success{
            echo "Sending message to Slack"
            slackSend (color: "${env.SLACK_COLOR_GOOD}",
                     channel: "#jenkins-notification",
                     message: "${custom_msg_notification('SUCCESS')}")
        }
        failure{
            echo "Sending message to Slack"
            slackSend (color: "${env.SLACK_COLOR_DANGER}",
                     channel: "#jenkins-notification",
                     message: "${custom_msg_notification('FAILED')}")
        }
    }
}