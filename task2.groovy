import hudson.model.FreeStyleProject;
import hudson.tasks.Shell;
import jenkins.model.Jenkins
import hudson.plugins.git.GitSCM
import com.cloudbees.hudson.plugins.folder.*
import hudson.plugins.ws_cleanup.*

def jobName = "MNT-CD-module9-extcreated-job"
def gitRepo = "https://github.com/gloovers/groovy-scripts.git"

job = Jenkins.instance.createProject(FreeStyleProject, jobName)
job.buildersList.add(new Shell('echo "hello world"'))

def Scm = new GitSCM(gitRepo)
Scm.branches = [new hudson.plugins.git.BranchSpec("*/master")]
job.scm = Scm
job.setDescription("TASK 9.2")
job.buildWrappersList.add(new PreBuildCleanup(null, true, "", ""))
job.save()

build = job.scheduleBuild2(5, new hudson.model.Cause.UserIdCause())

build.get()

