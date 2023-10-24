pipeline {
    agent any

	tools {
        // client -> node, backend -> gradle
		nodejs "node"
		gradle "gradle"
	}

    environment {
        // 변수로 쓸 값 달아두기
        DOCKER_REGISTRY = "eon8718/lot-fresh"
        CLIENT_IMAGE_TAG = "client"
		ADMIN_CLIENT_IMAGE_TAG = "admin-client"
        AUTH_SERVICE_IMAGE_TAG = "auth-service"
        USER_SERVICE_IMAGE_TAG = "user-service"
		CART_SERVICE_IMAGE_TAG = "cart-service"
        PRODUCT_SERVICE_IMAGE_TAG = "product-service"
		ORDER_SERVICE_IMAGE_TAG = "order-service"
		PAYMENT_SERVICE_IMAGE_TAG = "payment-service"
        STORAGE_SERVICE_IMAGE_TAG = "storage-service"
		DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    }

    stages {

		stage('dockerLogin') {
			steps {
                // $DOCKERHUB_CREDENTIALS_PSW 정보를 | 통해 --password에 안전하게 전달. Jenkins server credentials에 'dockerhub' ID로 네이밍해서 usr:psw 매칭되게 해뒀음.
        		sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
      		}
		}

		stage('env-setup') {
			steps {
				script {
					withCredentials([
						string(credentialsId: 'AUTH_DB_PORT', variable: 'AUTH_DB_PORT'),
						string(credentialsId: 'USER_DB_PORT', variable: 'USER_DB_PORT'),
						string(credentialsId: 'CART_DB_PORT', variable: 'CART_DB_PORT'),
						string(credentialsId: 'PRODUCT_DB_PORT', variable: 'PRODUCT_DB_PORT'),
						string(credentialsId: 'ORDER_DB_PORT', variable: 'ORDER_DB_PORT'),
						string(credentialsId: 'PAYMENT_DB_PORT', variable: 'PAYMENT_DB_PORT'),
						string(credentialsId: 'STORAGE_DB_PORT', variable: 'STORAGE_DB_PORT'),

						string(credentialsId: 'AUTH_DB_SCHEMA', variable: 'AUTH_DB_SCHEMA'),
						string(credentialsId: 'USER_DB_SCHEMA', variable: 'USER_DB_SCHEMA'),
						string(credentialsId: 'CART_DB_SCHEMA', variable: 'CART_DB_SCHEMA'),
						string(credentialsId: 'PRODUCT_DB_SCHEMA', variable: 'PRODUCT_DB_SCHEMA'),
						string(credentialsId: 'ORDER_DB_SCHEMA', variable: 'ORDER_DB_SCHEMA'),
						string(credentialsId: 'PAYMENT_DB_SCHEMA', variable: 'PAYMENT_DB_SCHEMA'),
						string(credentialsId: 'STORAGE_DB_SCHEMA', variable: 'STORAGE_DB_SCHEMA'),
						
						string(credentialsId: 'DB_IP', variable: 'DB_IP'),
						string(credentialsId: 'DB_USERNAME', variable: 'DB_USERNAME'),
						string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD'),
						string(credentialsId: 'REDIS_PORT', variable: 'REDIS_PORT')

					]) {
						sh """
							echo "AUTH_DB_PORT=$AUTH_DB_PORT" > env.list
							echo "USER_DB_PORT=$USER_DB_PORT" >> env.list
							echo "CART_DB_PORT=$CART_DB_PORT" >> env.list
							echo "PRODUCT_DB_PORT=$PRODUCT_DB_PORT" >> env.list
							echo "ORDER_DB_PORT=$ORDER_DB_PORT" >> env.list
							echo "PAYMENT_DB_PORT=$PAYMENT_DB_PORT" >> env.list
							echo "STORAGE_DB_PORT=$STORAGE_DB_PORT" >> env.list

							echo "AUTH_DB_SCHEMA=$AUTH_DB_SCHEMA" >> env.list
							echo "USER_DB_SCHEMA=$USER_DB_SCHEMA" >> env.list
							echo "CART_DB_SCHEMA=$CART_DB_SCHEMA" >> env.list
							echo "PRODUCT_DB_SCHEMA=$PRODUCT_DB_SCHEMA" >> env.list
							echo "ORDER_DB_SCHEMA=$ORDER_DB_SCHEMA" >> env.list
							echo "PAYMENT_DB_SCHEMA=$PAYMENT_DB_SCHEMA" >> env.list
							echo "STORAGE_DB_SCHEMA=$STORAGE_DB_SCHEMA" >> env.list

							echo "DB_IP=$DB_IP" >> env.list
							echo "DB_USERNAME=$DB_USERNAME" >> env.list
							echo "DB_PASSWORD=$DB_PASSWORD" >> env.list
							echo "REDIS_PORT=$REDIS_PORT" >> env.list

							scp -o StrictHostKeyChecking=no env.list ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com:~/env.list
						"""
					}
				}
			}
		}

		stage('build') {
            parallel {
                stage('client build') {
					when {
						allOf {
							expression {
								currentBuild.result == null || currentBuild.result == 'SUCCESS'
							}
							changeset "lotfresh-front/**"
						}
					}
					steps {
						dir('lotfresh-front') {
							sh 'npm install'
							// sh '''
                            //     echo -e "VUE_APP_KAKAOPAY_IMP='${VUE_APP_KAKAOPAY_IMP}' \
                            //     VUE_APP_API_OAUTH2_BASE_URL='${VUE_APP_API_OAUTH2_BASE_URL}' \
                            //     VUE_APP_GOOGLE_AUTH_URL='${VUE_APP_GOOGLE_AUTH_URL}' \
                            //     VUE_APP_TWITTER_AUTH_URL='${VUE_APP_TWITTER_AUTH_URL}' \
                            //     VUE_APP_API_AUTH_BASE_URL='${VUE_APP_API_AUTH_BASE_URL}' \
                            //     VUE_APP_API_USER_BASE_URL='${VUE_APP_API_USER_BASE_URL}' \
                            //     VUE_APP_API_BASE_URL='${VUE_APP_API_BASE_URL}'" > .env.local
                            // '''
							sh 'CI=false npm run build'
							sh 'docker build -t ${DOCKER_REGISTRY}:${CLIENT_IMAGE_TAG} .'
							sh 'docker push ${DOCKER_REGISTRY}:${CLIENT_IMAGE_TAG}'
						}
					}
					post {
						success {
							echo 'client build succeeded'
						}
						failure {
							echo 'client build failed'
						}
					}
        		}

				// stage('auth-service build') {
				// 	when {
				// 		allOf {
				// 			expression {
				// 				currentBuild.result == null || currentBuild.result == 'SUCCESS'
				// 			}
				// 			changeset "auth-service/**"
				// 		}
				// 	}
				// 	steps {
				// 		dir('auth-service') {
				// 			sh 'chmod +x ./gradlew'
				// 			sh './gradlew clean build'
				// 			sh 'docker build -t ${DOCKER_REGISTRY}:${AUTH_SERVICE_IMAGE_TAG} .'
				// 			sh 'docker push ${DOCKER_REGISTRY}:${AUTH_SERVICE_IMAGE_TAG}'
				// 		}
				// 	}
				// 	post {
				// 		success {
				// 			echo 'auth-service build succeeded'
				// 		}
				// 		failure {
				// 			echo 'auth-service build failed'
				// 		}
				// 	}
				// }

				// stage('user-service build') {
				// 	when {
				// 		allOf {
				// 			expression {
				// 				currentBuild.result == null || currentBuild.result == 'SUCCESS'
				// 			}
				// 			changeset "user-service/**"
				// 		}
				// 	}
				// 	steps {
				// 		dir('user-service') {
				// 			sh 'chmod +x ./gradlew'
				// 			sh './gradlew clean build'
				// 			sh 'docker build -t ${DOCKER_REGISTRY}:${USER_SERVICE_IMAGE_TAG} .'
				// 			sh 'docker push ${DOCKER_REGISTRY}:${USER_SERVICE_IMAGE_TAG}'
				// 		}
				// 	}
				// 	post {
				// 		success {
				// 			echo 'user-service build succeeded'
				// 		}
				// 		failure {
				// 			echo 'user-service build failed'
				// 		}
				// 	}
				// }

				// stage('cart-service build') {
				// 	when {
				// 		allOf {
				// 			expression {
				// 				currentBuild.result == null || currentBuild.result == 'SUCCESS'
				// 			}
				// 			changeset "cart-service/**"
				// 		}
				// 	}
				// 	steps {
				// 		dir('cart-service') {
				// 			sh 'chmod +x ./gradlew'
				// 			sh './gradlew clean build'
				// 			sh 'docker build -t ${DOCKER_REGISTRY}:${CART_SERVICE_IMAGE_TAG} .'
				// 			sh 'docker push ${DOCKER_REGISTRY}:${CART_SERVICE_IMAGE_TAG}'
				// 		}
				// 	}
				// 	post {
				// 		success {
				// 			echo 'cart-service build succeeded'
				// 		}
				// 		failure {
				// 			echo 'cart-service build failed'
				// 		}
				// 	}
				// }

				stage('product-service build') {
					when {
						allOf {
							expression {
								currentBuild.result == null || currentBuild.result == 'SUCCESS'
							}
							changeset "product-service/**"
						}
					}
					steps {
						dir('product-service') {
							sh 'chmod +x ./gradlew'
							sh './gradlew clean assemble'
							sh 'docker build -t ${DOCKER_REGISTRY}:${PRODUCT_SERVICE_IMAGE_TAG} .'
							sh 'docker push ${DOCKER_REGISTRY}:${PRODUCT_SERVICE_IMAGE_TAG}'
						}
					}
					post {
						success {
							echo 'product-service build succeeded'
						}
						failure {
							echo 'product-service build failed'
						}
					}
				}

				stage('order-service build') {
					when {
						allOf {
							expression {
								currentBuild.result == null || currentBuild.result == 'SUCCESS'
							}
							changeset "order-service/**"
						}
					}
					steps {
						dir('order-service') {
							sh 'chmod +x ./gradlew'
							sh './gradlew clean assemble'
							sh 'docker build -t ${DOCKER_REGISTRY}:${ORDER_SERVICE_IMAGE_TAG} .'
							sh 'docker push ${DOCKER_REGISTRY}:${ORDER_SERVICE_IMAGE_TAG}'
						}
					}
					post {
						success {
							echo 'order-service build succeeded'
						}
						failure {
							echo 'order-service build failed'
						}
					}
				}

				stage('payment-service build') {
					when {
						allOf {
							expression {
								currentBuild.result == null || currentBuild.result == 'SUCCESS'
							}
							changeset "payment-service/**"
						}
					}
					steps {
						dir('payment-service') {
							sh 'chmod +x ./gradlew'
							sh './gradlew clean assemble'
							sh 'docker build -t ${DOCKER_REGISTRY}:${PAYMENT_SERVICE_IMAGE_TAG} .'
							sh 'docker push ${DOCKER_REGISTRY}:${PAYMENT_SERVICE_IMAGE_TAG}'
						}
					}
					post {
						success {
							echo 'payment-service build succeeded'
						}
						failure {
							echo 'payment-service build failed'
						}
					}
				}

                stage('storage-service build') {
					when {
						allOf {
							expression {
								currentBuild.result == null || currentBuild.result == 'SUCCESS'
							}
							changeset "storage-service/**"
						}
					}
					steps {
						dir('storage-service') {
							sh 'chmod +x ./gradlew'
							sh './gradlew clean assemble'
							sh 'docker build -t ${DOCKER_REGISTRY}:${STORAGE_SERVICE_IMAGE_TAG} .'
							sh 'docker push ${DOCKER_REGISTRY}:${STORAGE_SERVICE_IMAGE_TAG}'
						}
					}
					post {
						success {
							echo 'storage-service build succeeded'
						}
						failure {
							echo 'storage-service build failed'
						}
					}
				}

			}
        }
        
        stage('deploy') {
            parallel {
                stage('replace client container') {
                    when {
                    	allOf {
                      		expression {
                        		currentBuild.result == null || currentBuild.result == 'SUCCESS'
                      		}
                      		changeset "lotfresh-front/**"
                    	}
                  	}
                    steps {
                        script {
            				sshagent(credentials: ['ssh']) {
								sh """
									if ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container ls -a | grep -q ${CLIENT_IMAGE_TAG}; then
										ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container stop ${CLIENT_IMAGE_TAG}
									fi
									ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker run -p 80:80 --name ${CLIENT_IMAGE_TAG} --network lot-fresh -d --rm ${DOCKER_REGISTRY}:${CLIENT_IMAGE_TAG}
								"""
            				}
        				}
		           	}
                }

                // stage('replace auth-service container') {
                //     when {
                //     	allOf {
                //       		expression {
                //         		currentBuild.result == null || currentBuild.result == 'SUCCESS'
                //       		}
				// 			changeset "auth-service/**"
                //     	}
                //   	}
                //     steps {
				// 		script {
            	// 			sshagent(credentials: ['ssh']) {
				// 				sh """
				// 					if ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container ls -a | grep -q ${AUTH_SERVICE_IMAGE_TAG}; then
				// 						ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container stop ${AUTH_SERVICE_IMAGE_TAG}
				// 					fi
				// 					ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker run -p 8080:8080 --name ${AUTH_SERVICE_IMAGE_TAG} --network lot-fresh -d --rm ${DOCKER_REGISTRY}:${AUTH_SERVICE_IMAGE_TAG}
				// 				"""
            	// 			}
        		// 		}
                //     }
                // }

				// stage('replace user-service container') {
                //     when {
                //     	allOf {
                //       		expression {
                //         		currentBuild.result == null || currentBuild.result == 'SUCCESS'
                //       		}
				// 			changeset "user-service/**"
                //     	}
                //   	}
                //     steps {
				// 		script {
            	// 			sshagent(credentials: ['ssh']) {
				// 				sh """
				// 					if ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container ls -a | grep -q ${USER_SERVICE_IMAGE_TAG}; then
				// 						ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container stop ${USER_SERVICE_IMAGE_TAG}
				// 					fi
				// 					ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker run -p 8081:8081 --name ${USER_SERVICE_IMAGE_TAG} --network lot-fresh -d --rm ${DOCKER_REGISTRY}:${USER_SERVICE_IMAGE_TAG}
				// 				"""
            	// 			}
        		// 		}
                //     }
                // }

				// stage('replace cart-service container') {
                //     when {
                //     	allOf {
                //       		expression {
                //         		currentBuild.result == null || currentBuild.result == 'SUCCESS'
                //       		}
				// 			changeset "cart-service/**"
                //     	}
                //   	}
                //     steps {
				// 		script {
            	// 			sshagent(credentials: ['ssh']) {
				// 				sh """
				// 					if ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container ls -a | grep -q ${CART_SERVICE_IMAGE_TAG}; then
				// 						ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container stop ${CART_SERVICE_IMAGE_TAG}
				// 					fi
				// 					ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker run -p 8082:8082 --name ${CART_SERVICE_IMAGE_TAG} --network lot-fresh -d --rm ${DOCKER_REGISTRY}:${CART_SERVICE_IMAGE_TAG}
				// 				"""
            	// 			}
        		// 		}
                //     }
                // }

				stage('replace product-service container') {
                    when {
                    	allOf {
                      		expression {
                        		currentBuild.result == null || currentBuild.result == 'SUCCESS'
                      		}
							changeset "product-service/**"
                    	}
                  	}
                    steps {
						script {
            				sshagent(credentials: ['ssh']) {
									sh """
										if ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container ls -a | grep -q ${PRODUCT_SERVICE_IMAGE_TAG}; then
											ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container stop ${PRODUCT_SERVICE_IMAGE_TAG}
										fi
										
										ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker run -p 8083:8083 --name ${PRODUCT_SERVICE_IMAGE_TAG} --network lot-fresh -d --rm --env-file ~/env.list ${DOCKER_REGISTRY}:${PRODUCT_SERVICE_IMAGE_TAG}

									"""
            				}
        				}
                    }
                }

				stage('replace order-service container') {
                    when {
                    	allOf {
                      		expression {
                        		currentBuild.result == null || currentBuild.result == 'SUCCESS'
                      		}
							changeset "order-service/**"
                    	}
                  	}
                    steps {
						script {
            				sshagent(credentials: ['ssh']) {
									sh """
										if ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container ls -a | grep -q ${ORDER_SERVICE_IMAGE_TAG}; then
											ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container stop ${ORDER_SERVICE_IMAGE_TAG}
										fi
										ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker run -p 8084:8084 --name ${ORDER_SERVICE_IMAGE_TAG} --network lot-fresh -d --rm --env-file ~/env.list ${DOCKER_REGISTRY}:${ORDER_SERVICE_IMAGE_TAG}
									"""
								
            				}
        				}
                    }
                }

				stage('replace payment-service container') {
                    when {
                    	allOf {
                      		expression {
                        		currentBuild.result == null || currentBuild.result == 'SUCCESS'
                      		}
							changeset "payment-service/**"
                    	}
                  	}
                    steps {
						script {
            				sshagent(credentials: ['ssh']) {
								sh """
									if ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container ls -a | grep -q ${PAYMENT_SERVICE_IMAGE_TAG}; then
										ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container stop ${PAYMENT_SERVICE_IMAGE_TAG}
									fi
									ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker run -p 8085:8085 --name ${PAYMENT_SERVICE_IMAGE_TAG} --network lot-fresh -d --rm --env-file ~/env.list ${DOCKER_REGISTRY}:${PAYMENT_SERVICE_IMAGE_TAG}
								"""
            				}
        				}
                    }
                }

				stage('replace storage-service container') {
                    when {
                    	allOf {
                      		expression {
                        		currentBuild.result == null || currentBuild.result == 'SUCCESS'
                      		}
							changeset "storage-service/**"
                    	}
                  	}
                    steps {
						script {
            				sshagent(credentials: ['ssh']) {
								sh """
									if ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container ls -a | grep -q ${STORAGE_SERVICE_IMAGE_TAG}; then
										ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker container stop ${STORAGE_SERVICE_IMAGE_TAG}
									fi
									ssh -o StrictHostKeyChecking=no ubuntu@ec2-52-78-250-117.ap-northeast-2.compute.amazonaws.com docker run -p 8086:8086 --name ${STORAGE_SERVICE_IMAGE_TAG} --network lot-fresh -d --rm --env-file ~/env.list ${DOCKER_REGISTRY}:${STORAGE_SERVICE_IMAGE_TAG}
								"""
            				}
        				}
                    }
                }
            }
        }
  	}
}