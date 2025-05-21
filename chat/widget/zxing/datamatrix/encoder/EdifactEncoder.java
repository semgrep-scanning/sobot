package com.sobot.chat.widget.zxing.datamatrix.encoder;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: source-8303388-dex2jar.jar:com/sobot/chat/widget/zxing/datamatrix/encoder/EdifactEncoder.class */
public final class EdifactEncoder implements Encoder {
    private static void encodeChar(char c2, StringBuilder sb) {
        if (c2 >= ' ' && c2 <= '?') {
            sb.append(c2);
        } else if (c2 < '@' || c2 > '^') {
            HighLevelEncoder.illegalCharacter(c2);
        } else {
            sb.append((char) (c2 - '@'));
        }
    }

    private static String encodeToCodewords(CharSequence charSequence, int i) {
        int length = charSequence.length() - i;
        if (length != 0) {
            char charAt = charSequence.charAt(i);
            char c2 = 0;
            char charAt2 = length >= 2 ? charSequence.charAt(i + 1) : (char) 0;
            char charAt3 = length >= 3 ? charSequence.charAt(i + 2) : (char) 0;
            if (length >= 4) {
                c2 = charSequence.charAt(i + 3);
            }
            int i2 = (charAt << 18) + (charAt2 << '\f') + (charAt3 << 6) + c2;
            char c3 = (char) ((i2 >> 16) & 255);
            char c4 = (char) ((i2 >> 8) & 255);
            char c5 = (char) (i2 & 255);
            StringBuilder sb = new StringBuilder(3);
            sb.append(c3);
            if (length >= 2) {
                sb.append(c4);
            }
            if (length >= 3) {
                sb.append(c5);
            }
            return sb.toString();
        }
        throw new IllegalStateException("StringBuilder must not be empty");
    }

    private static void handleEOD(EncoderContext encoderContext, CharSequence charSequence) {
        try {
            int length = charSequence.length();
            if (length == 0) {
                return;
            }
            if (length == 1) {
                encoderContext.updateSymbolInfo();
                int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount();
                int remainingCharacters = encoderContext.getRemainingCharacters();
                int i = dataCapacity;
                if (remainingCharacters > dataCapacity) {
                    encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + 1);
                    i = encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount();
                }
                if (remainingCharacters <= i && i <= 2) {
                    return;
                }
            }
            if (length > 4) {
                throw new IllegalStateException("Count must not exceed 4");
            }
            int i2 = length - 1;
            String encodeToCodewords = encodeToCodewords(charSequence, 0);
            boolean z = (encoderContext.hasMoreCharacters() ^ true) && i2 <= 2;
            boolean z2 = z;
            if (i2 <= 2) {
                encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + i2);
                z2 = z;
                if (encoderContext.getSymbolInfo().getDataCapacity() - encoderContext.getCodewordCount() >= 3) {
                    encoderContext.updateSymbolInfo(encoderContext.getCodewordCount() + encodeToCodewords.length());
                    z2 = false;
                }
            }
            if (z2) {
                encoderContext.resetSymbolInfo();
                encoderContext.pos -= i2;
            } else {
                encoderContext.writeCodewords(encodeToCodewords);
            }
        } finally {
            encoderContext.signalEncoderChange(0);
        }
    }

    @Override // com.sobot.chat.widget.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            encodeChar(encoderContext.getCurrentChar(), sb);
            encoderContext.pos++;
            if (sb.length() >= 4) {
                encoderContext.writeCodewords(encodeToCodewords(sb, 0));
                sb.delete(0, 4);
                if (HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode()) != getEncodingMode()) {
                    encoderContext.signalEncoderChange(0);
                    break;
                }
            }
        }
        sb.append((char) 31);
        handleEOD(encoderContext, sb);
    }

    @Override // com.sobot.chat.widget.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 4;
    }
}
