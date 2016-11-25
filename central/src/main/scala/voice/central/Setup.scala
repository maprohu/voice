package voice.central

/**
  * Created by pappmar on 25/11/2016.
  */
object Setup {

  case class AsRoot(
    commands: String*
  )
  val Commands = Seq(
    "sudo add-apt-repository -y ppa:webupd8team/java",
    "sudo apt-get update",
    "echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections",
    "echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections",
    "sudo apt-get -y install oracle-java8-installer",
    "sudo apt-get install oracle-java8-set-default",
    "mkdir -p ~/.ammonite && curl -L -o ~/.ammonite/predef.sc https://git.io/v6r5y",
    "sudo curl -L -o /usr/local/bin/amm https://git.io/vXVf5 && sudo chmod +x /usr/local/bin/amm",
    "sudo fallocate -l 1G /swapfile",
    "sudo chmod 600 /swapfile",
    "sudo mkswap /swapfile",
    "sudo swapon /swapfile",
    "sudo cp /etc/fstab /etc/fstab.bak",
    "echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab",
    "sudo sysctl vm.swappiness=10",
    "echo vm.swappiness=10 | sudo tee -a /etc/sysctl.d/99-swappiness.conf",
    "echo vm.vfs_cache_pressure=50 | sudo tee -a /etc/sysctl.d/99-swappiness.conf"

  )

}
