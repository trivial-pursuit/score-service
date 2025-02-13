#!/bin/sh

usage() {
	echo "Usage: \nuse or -s to start or -d to start with Keycloak DEV or -q to stop the local environment"
}

start_env() {
	echo "Starting local environment ..."
	docker compose -f docker-compose.yml -f docker-compose_keycloak.yml up --build
}

start_env_keycloak_dev() {
	echo "Starting local environment ..."
	docker compose -f docker-compose.yml -f docker-compose_keycloak-dev.yml up
}

stop_env() {
	echo "Stopping local environment ..."
	docker compose -f docker-compose.yml -f docker-compose_keycloak.yml down
}

###
# Main
###
if [ $# -eq 0 ]; then
	usage
fi

while getopts ":sdqh" opt; do
	case $opt in
	s)
		start_env
		;;
	d)
		start_env_keycloak_dev
		;;
	q)
		stop_env
		;;
	h)
		usage
		;;
	\?)
		echo "Invalid option"
		exit 1
		;;
	esac
done
