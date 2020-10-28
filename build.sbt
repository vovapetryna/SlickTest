lazy val model =
  Project(id = "model", base = file("model"))
    .settings(
      version := "0.1",
      scalaVersion := "2.12.6"
    )

lazy val dataTables =
  Project(id = "datatables", base = file("datatables"))
    .dependsOn(model)
    .settings(
      version := "0.1",
      scalaVersion := "2.12.6",
      libraryDependencies ++= Seq(
        "com.typesafe.slick" %% "slick" % "3.2.3",
        "org.slf4j" % "slf4j-nop" % "1.6.4",
        "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3",
        "org.postgresql" % "postgresql" % "42.2.18",
        "com.h2database" % "h2" % "1.4.200"
      )
    )

lazy val application =
  Project(id = "application", base = file("application"))
    .dependsOn(dataTables)
    .settings(
      version := "0.1",
      scalaVersion := "2.12.6",
      wartremoverErrors ++= Warts.unsafe ++ Seq(Wart.Recursion, Wart.While),
      wartremoverExcluded += baseDirectory.value / "src" / "test" / "scala" / "Lab2Spec.scala",
      wartremoverExcluded += baseDirectory.value / "src" / "test" / "scala" / "Lab2_0Spec.scala",
      wartremoverExcluded += baseDirectory.value / "src" / "test" / "scala" / "Lab2_1Spec.scala",

      libraryDependencies ++= Seq(
        "org.scalactic" %% "scalactic" % "3.0.5",
        "org.scalatest" %% "scalatest" % "3.0.5" % "test"
      ),
      parallelExecution in Test := false
    )

lazy val root =
  Project("lab2", file("."))
    .aggregate(application)
