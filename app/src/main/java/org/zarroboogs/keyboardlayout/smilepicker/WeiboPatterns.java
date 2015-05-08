
package org.zarroboogs.keyboardlayout.smilepicker;

import java.util.regex.Pattern;

public class WeiboPatterns {

    public static final Pattern WEB_URL = Pattern.compile("http://[a-zA-Z0-9+&@#/%?=~_\\-|!:,\\.;]*[a-zA-Z0-9+&@#/%=~_|]");

    public static final Pattern TOPIC_URL = Pattern.compile("#[\\p{Print}\\p{InCJKUnifiedIdeographs}&&[^#]]+#");

    public static final Pattern MENTION_URL = Pattern.compile("@[\\w\\p{InCJKUnifiedIdeographs}-]{1,26}");

    public static final Pattern EMOTION_URL = Pattern.compile("\\[(\\S+?)\\]");

    public static final String WEB_SCHEME = "http://";

    public static final String TOPIC_SCHEME = "org.zarroboogs.weibo.topic://";

    public static final String MENTION_SCHEME = "org.zarroboogs.weibo://";

}
