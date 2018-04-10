/*
 * MyTake.org
 *
 *  Copyright 2017 by its authors.
 *  Some rights reserved. See LICENSE, https://github.com/mytakedotorg/mytakedotorg/graphs/contributors
 */
package org.mytake.foundation;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Folders {
	public static Path SRC_DOCUMENT = Paths.get("src/main/resources/document");
	public static Path SRC_PRESIDENTIAL_DEBATES = Paths.get("../presidential-debates");
	public static Path DST_FOUNDATION_RESOURCES = Paths.get("../foundation/src/main/resources");
	public static Path DST_FOUNDATION_DATA = DST_FOUNDATION_RESOURCES.resolve("foundation-data");
}
