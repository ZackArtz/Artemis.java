workflow "New workflow" {  
  on = "push"
}

action "Build Docker image" {  
  uses = "actions/docker/cli@master"
  args = ["build", "-t", "zackartz/artemis:$(echo $GITHUB_SHA | head -c7)", "."]
}
