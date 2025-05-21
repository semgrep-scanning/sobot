package com.sobot.chat.widget.zxing.datamatrix;

import com.sobot.chat.widget.zxing.BarcodeFormat;
import com.sobot.chat.widget.zxing.BinaryBitmap;
import com.sobot.chat.widget.zxing.ChecksumException;
import com.sobot.chat.widget.zxing.DecodeHintType;
import com.sobot.chat.widget.zxing.FormatException;
import com.sobot.chat.widget.zxing.NotFoundException;
import com.sobot.chat.widget.zxing.Reader;
import com.sobot.chat.widget.zxing.Result;
import com.sobot.chat.widget.zxing.ResultMetadataType;
import com.sobot.chat.widget.zxing.ResultPoint;
import com.sobot.chat.widget.zxing.common.BitMatrix;
import com.sobot.chat.widget.zxing.common.DecoderResult;
import com.sobot.chat.widget.zxing.common.DetectorResult;
import com.sobot.chat.widget.zxing.datamatrix.decoder.Decoder;
import com.sobot.chat.widget.zxing.datamatrix.detector.Detector;
import java.util.List;
import java.util.Map;

/* loaded from: source-8303388-dex2jar.jar:com/sobot/chat/widget/zxing/datamatrix/DataMatrixReader.class */
public final class DataMatrixReader implements Reader {
    private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
    private final Decoder decoder = new Decoder();

    private static BitMatrix extractPureBits(BitMatrix bitMatrix) throws NotFoundException {
        int[] topLeftOnBit = bitMatrix.getTopLeftOnBit();
        int[] bottomRightOnBit = bitMatrix.getBottomRightOnBit();
        if (topLeftOnBit == null || bottomRightOnBit == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        int moduleSize = moduleSize(topLeftOnBit, bitMatrix);
        int i = topLeftOnBit[1];
        int i2 = bottomRightOnBit[1];
        int i3 = topLeftOnBit[0];
        int i4 = ((bottomRightOnBit[0] - i3) + 1) / moduleSize;
        int i5 = ((i2 - i) + 1) / moduleSize;
        if (i4 <= 0 || i5 <= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i6 = moduleSize / 2;
        BitMatrix bitMatrix2 = new BitMatrix(i4, i5);
        int i7 = 0;
        while (true) {
            int i8 = i7;
            if (i8 >= i5) {
                return bitMatrix2;
            }
            int i9 = 0;
            while (true) {
                int i10 = i9;
                if (i10 < i4) {
                    if (bitMatrix.get((i10 * moduleSize) + i3 + i6, (i8 * moduleSize) + i + i6)) {
                        bitMatrix2.set(i10, i8);
                    }
                    i9 = i10 + 1;
                }
            }
            i7 = i8 + 1;
        }
    }

    private static int moduleSize(int[] iArr, BitMatrix bitMatrix) throws NotFoundException {
        int width = bitMatrix.getWidth();
        int i = iArr[0];
        int i2 = iArr[1];
        while (i < width && bitMatrix.get(i, i2)) {
            i++;
        }
        if (i != width) {
            int i3 = i - iArr[0];
            if (i3 != 0) {
                return i3;
            }
            throw NotFoundException.getNotFoundInstance();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.sobot.chat.widget.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return decode(binaryBitmap, null);
    }

    @Override // com.sobot.chat.widget.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        DecoderResult decode;
        ResultPoint[] points;
        if (map == null || !map.containsKey(DecodeHintType.PURE_BARCODE)) {
            DetectorResult detect = new Detector(binaryBitmap.getBlackMatrix()).detect();
            decode = this.decoder.decode(detect.getBits());
            points = detect.getPoints();
        } else {
            decode = this.decoder.decode(extractPureBits(binaryBitmap.getBlackMatrix()));
            points = NO_POINTS;
        }
        Result result = new Result(decode.getText(), decode.getRawBytes(), points, BarcodeFormat.DATA_MATRIX);
        List<byte[]> byteSegments = decode.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String eCLevel = decode.getECLevel();
        if (eCLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, eCLevel);
        }
        return result;
    }

    @Override // com.sobot.chat.widget.zxing.Reader
    public void reset() {
    }
}
