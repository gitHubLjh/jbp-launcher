/*
 * Copyright (C) 2012~2013 dinstone<dinstone@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dinstone.launcher;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * java application launcher.
 * 
 * @author dinstone
 * @version 2.0.0
 */
public class Launcher {

    private static final Logger LOG = Logger.getLogger(Launcher.class.getName());

    private LifecycleManager lifecycle;

    public Launcher() {
        init();
    }

    public void start() throws Exception {
        lifecycle.start();
    }

    public void stop() throws Exception {
        lifecycle.stop();
    }

    public static void main(String[] args) {
        try {
            Launcher launcher = new Launcher();

            String command = "start";
            if (args.length > 0) {
                command = args[args.length - 1];
            }

            if (command.equals("start")) {
                launcher.start();

                // Forced to exit the JVM
                System.exit(0);
            } else if (command.equals("stop")) {
                launcher.stop();
            } else {
                LOG.warning("Java launcher does not support this command: \"" + command + "\".");
            }
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(-1);
        }

    }

    private void init() {
        try {
            Configuration config = new Configuration();

            String launcherHome = config.getLauncherHome();
            LOG.info("launcher.home is " + launcherHome);

            String applicationHome = config.getApplicationHome();
            LOG.info("application.home is " + applicationHome);

            lifecycle = new LifecycleManager(config);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "launcher init error.", e);
            throw new RuntimeException(e);
        }
    }

}
