package swen221.monopoly.testing;

import org.junit.*;
import static org.junit.Assert.*;
import swen221.monopoly.locations.*;

/**
 * Created on 8/06/2017.
 * Name: Christopher Straight
 * Usercode: straigchri
 * ID: 300363269
 */
public class SpecialAreaTests {

    @Test
    public void test_hasOwner(){
        SpecialArea specialArea = new SpecialArea("Go");
        assertFalse(specialArea.hasOwner());
    }

    @Test
    public void test_getOwner(){
        SpecialArea specialArea = new SpecialArea("Go");
        try{
            specialArea.getOwner();
            fail();
        } catch (RuntimeException ignored){}
    }

    @Test
    public void test_getRent(){
        SpecialArea specialArea = new SpecialArea("Go");
        try{
            specialArea.getRent();
            fail();
        } catch (RuntimeException ignored){}
    }
}
                                                                                                                                                                        name="MAIN_CLASS_NAME" value="swen221.monopoly.testing.BoardTests" />
<option name="METHOD_NAME" value="" />
<option name="TEST_OBJECT" value="class" />
<option name="VM_PARAMETERS" value="-ea" />
<option name="PARAMETERS" value="" />
<option name="WORKING_DIRECTORY" value="file://$MODULE_DIR$" />
<option name="ENV_VARIABLES" />
<option name="PASS_PARENT_ENVS" value="true" />
<option name="TEST_SEARCH_SCOPE">
<value defaultName="singleModule" />
</option>
<envs />
<patterns />
<method />
</configuration>
<configuration default="false" name="TestSuite" type="JUnit" factoryName="JUnit" temporary="true" nameIsGenerated="true">
<extension name="coverage" enabled="false" merge="false" sample_coverage="true" runner="idea">
<pattern>
<option name="PATTERN" value="swen221.monopoly.*" />
<option name="ENABLED" value="true" />
</pattern>
</extension>
<module name="ass5" />
<option name="ALTERNATIVE_JRE_PATH_ENABLED" value="false" />
<option name="ALTERNATIVE_JRE_PATH" />
<option name="PACKAGE_NAME" value="swen221.monopoly.testing" />
<option name="MAIN_CLASS_NAME" value="swen221.monopoly.testing.TestSuite" />
<option name="METHOD_NAME" value="" />
<option name="TEST_OBJECT" value="class" />
<option name="VM_PARAMETERS" value="-ea" />
<option name="PARAMETERS" value="" />
<option name="WORKING_DIRECTORY" value="file://$MODULE_DIR$" />
<option name="ENV_VARIABLES" />
<option name="PASS_PARENT_ENVS" value="true" />
<option name="TEST_SEARCH_SCOPE">
<value defaultName="singleModule" />
</option>
<envs />
<patterns />
<method />
</configuration>
<configuration default="true" type="#org.jetbrains.idea.devkit.run.PluginConfigurationType" factoryName="Plugin">
<module name="" />
<option name="VM_PARAMETERS" value="-Xmx512m -Xms256m -XX:MaxPermSize=250m -ea" />
<option name="PROGRAM_PARAMETERS" />
<predefined_log_file id="idea.log" enabled="true" />
<method />
</configuration>
<configuration default="true" type="AndroidRunConfigurationType" factoryName="Android App">
<module name="" />
<option name="DEPLOY" value="true" />
<option name="ARTIFACT_NAME" value="" />
<option name="PM_INSTALL_OPTIONS" value="" />
<option name="ACTIVITY_EXTRA_FLAGS" value="" />
<option name="MODE" value="default_activity" />
<option name="TARGET_SELECTION_MODE" value="SHOW_DIALOG" />
<option name="PREFERRED_AVD" value="" />
<option name="CLEAR_LOGCAT" value="false" />
<option name="SHOW_LOGCAT_AUTOMATICALLY" value="false" />
<option name="SKIP_NOOP_APK_INSTALLATIONS" value="true" />
<option name="FORCE_STOP_RUNNING_APP" value="true" />
<option name="DEBUGGER_TYPE" value="Java" />
<option name="USE_LAST_SELECTED_DEVICE" value="false" />
<option name="PREFERRED_AVD" value="" />

