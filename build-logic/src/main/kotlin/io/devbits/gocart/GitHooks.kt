/*
 * Copyright 2023 Eton Otieno
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.devbits.gocart

import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.register

internal fun Project.configureGitHooks() {
    tasks.register<Copy>("copyGitHooks") {
        description = "Copies the git hooks from /git-hooks to the .git folder."
        from("$rootDir/scripts/git-hooks/") {
            include("**/*.sh")
            rename("(.*).sh", "\$1")
        }
        into("$rootDir/.git/hooks")
        onlyIf { isLinuxOrMacOs() }
    }

    tasks.register<Exec>("installGitHooks") {
        description = "Installs the pre-commit git hooks from scripts/git-hooks."
        group = "git hooks"
        workingDir = rootDir
        commandLine = listOf("chmod")
        args(listOf("-R", "+x", ".git/hooks/"))
        dependsOn("copyGitHooks")
        onlyIf { isLinuxOrMacOs() }
        doLast {
            logger.info("Git hook installed successfully.")
        }
    }
}

private fun isLinuxOrMacOs(): Boolean {
    val osName = System.getProperty("os.name").lowercase()
    return osName.contains("linux") || osName.contains("mac os") || osName.contains("macos")
}
