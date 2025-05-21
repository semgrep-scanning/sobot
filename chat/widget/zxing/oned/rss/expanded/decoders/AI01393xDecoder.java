package com.sobot.chat.widget.zxing.oned.rss.expanded.decoders;

import com.sobot.chat.widget.zxing.FormatException;
import com.sobot.chat.widget.zxing.NotFoundException;
import com.sobot.chat.widget.zxing.common.BitArray;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: source-8303388-dex2jar.jar:com/sobot/chat/widget/zxing/oned/rss/expanded/decoders/AI01393xDecoder.class */
public final class AI01393xDecoder extends AI01decoder {
    private static final int FIRST_THREE_DIGITS_SIZE = 10;
    private static final int HEADER_SIZE = 8;
    private static final int LAST_DIGIT_SIZE = 2;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AI01393xDecoder(BitArray bitArray) {
        super(bitArray);
    }

    @Override // com.sobot.chat.widget.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public String parseInformation() throws NotFoundException, FormatException {
        if (getInformation().getSize() >= 48) {
            StringBuilder sb = new StringBuilder();
            encodeCompressedGtin(sb, 8);
            int extractNumericValueFromBitArray = getGeneralDecoder().extractNumericValueFromBitArray(48, 2);
            sb.append("(393");
            sb.append(extractNumericValueFromBitArray);
            sb.append(')');
            int extractNumericValueFromBitArray2 = getGeneralDecoder().extractNumericValueFromBitArray(50, 10);
            if (extractNumericValueFromBitArray2 / 100 == 0) {
                sb.append('0');
            }
            if (extractNumericValueFromBitArray2 / 10 == 0) {
                sb.append('0');
            }
            sb.append(extractNumericValueFromBitArray2);
            sb.append(getGeneralDecoder().decodeGeneralPurposeField(60, null).getNewString());
            return sb.toString();
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
