pipeline {
  agent any

  options { 
    timestamps() 
  }

  environment {
    MAVEN_OPTS = "-Dfile.encoding=UTF-8"
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build & Test') {
      steps {
        echo "Running Maven tests with full logs..."
        sh 'mvn -Dheadless=true -B clean test'   // Add -X for super detailed logs
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
          archiveArtifacts artifacts: 'target/surefire-reports/**/*', fingerprint: true

          // Capture console log
          sh 'curl "$BUILD_URL/consoleText" -o target/console.log || true'
          archiveArtifacts artifacts: 'target/console.log', fingerprint: true
        }
      }
    }

    stage('Rerun Failed (if any)') {
      when {
        expression { fileExists('target/rerun.txt') && readFile('target/rerun.txt').trim() }
      }
      steps {
        echo "Re-running failed scenarios..."
        sh 'mvn -Dheadless=true -B test -Dtest=runner.RerunFailedRunner'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
          archiveArtifacts artifacts: 'target/**', fingerprint: true
        }
      }
    }
  }

  post {
    always {
      echo "Build finished: ${currentBuild.currentResult}"

      // Capture Jenkins console output AGAIN at pipeline end
      sh 'curl "$BUILD_URL/consoleText" -o target/final-console.log || true'
      archiveArtifacts artifacts: 'target/final-console.log', fingerprint: true
    }
  }
}