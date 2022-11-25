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
                    sh "cd ./infrastructure/docker-compose"
                    sh "pwd"
//                     jacoco(execPattern: 'target/jacoco.exec')
                }
            }
        }

        stage('SonarQube Analysis') {
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
                    sh """mvn -s settings.xml clean verify sonar:sonar -Dsonar.projectKey=api-microservice-pbl6" \
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
                            error "Quality gate is broken"
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
                    sh "cd infrastructure/docker-compose"
                    sh "docker-compose -f common.yml -f service.yml build"
                    sh "docker login -u vanket -p dckr_pat_V1ZSZ0lJu4IESrxEJz_45ClFc60"
                    sh "docker push vanket/microservice-pbl6:user-service"
                    sh "docker push vanket/microservice-pbl6:member-service"
                    sh "docker push vanket/microservice-pbl6:organization-service"
                    sh "docker push vanket/microservice-pbl6:issues-service"

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