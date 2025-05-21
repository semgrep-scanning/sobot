package com.sobot.chat.widget.zxing.client.result;

import com.sobot.chat.widget.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: source-8303388-dex2jar.jar:com/sobot/chat/widget/zxing/client/result/GeoResultParser.class */
public final class GeoResultParser extends ResultParser {
    private static final Pattern GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

    @Override // com.sobot.chat.widget.zxing.client.result.ResultParser
    public GeoParsedResult parse(Result result) {
        Matcher matcher = GEO_URL_PATTERN.matcher(getMassagedText(result));
        if (matcher.matches()) {
            String group = matcher.group(4);
            try {
                double parseDouble = Double.parseDouble(matcher.group(1));
                if (parseDouble > 90.0d || parseDouble < -90.0d) {
                    return null;
                }
                double parseDouble2 = Double.parseDouble(matcher.group(2));
                if (parseDouble2 > 180.0d || parseDouble2 < -180.0d) {
                    return null;
                }
                double d = 0.0d;
                if (matcher.group(3) != null) {
                    d = Double.parseDouble(matcher.group(3));
                    if (d < 0.0d) {
                        return null;
                    }
                }
                return new GeoParsedResult(parseDouble, parseDouble2, d, group);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
