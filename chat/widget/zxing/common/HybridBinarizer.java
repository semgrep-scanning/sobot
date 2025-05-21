package com.sobot.chat.widget.zxing.common;

import com.sobot.chat.widget.zxing.Binarizer;
import com.sobot.chat.widget.zxing.LuminanceSource;
import com.sobot.chat.widget.zxing.NotFoundException;
import java.lang.reflect.Array;

/* loaded from: source-8303388-dex2jar.jar:com/sobot/chat/widget/zxing/common/HybridBinarizer.class */
public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    private static int[][] calculateBlackPoints(byte[] bArr, int i, int i2, int i3, int i4) {
        int i5 = i4 - 8;
        int i6 = i3 - 8;
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, i2, i);
        int i7 = 0;
        while (true) {
            int i8 = i7;
            if (i8 >= i2) {
                return iArr;
            }
            int i9 = i8 << 3;
            int i10 = i9;
            if (i9 > i5) {
                i10 = i5;
            }
            int i11 = 0;
            while (true) {
                int i12 = i11;
                if (i12 < i) {
                    int i13 = i12 << 3;
                    int i14 = i13;
                    if (i13 > i6) {
                        i14 = i6;
                    }
                    int i15 = (i10 * i3) + i14;
                    int i16 = 255;
                    int i17 = 0;
                    int i18 = 0;
                    int i19 = 0;
                    while (i17 < 8) {
                        int i20 = 0;
                        while (i20 < 8) {
                            int i21 = bArr[i15 + i20] & 255;
                            int i22 = i18 + i21;
                            int i23 = i16;
                            if (i21 < i16) {
                                i23 = i21;
                            }
                            int i24 = i19;
                            if (i21 > i19) {
                                i24 = i21;
                            }
                            i20++;
                            i19 = i24;
                            i16 = i23;
                            i18 = i22;
                        }
                        int i25 = i15;
                        int i26 = i17;
                        int i27 = i18;
                        if (i19 - i16 > 24) {
                            int i28 = i17;
                            int i29 = i15;
                            while (true) {
                                int i30 = i28 + 1;
                                int i31 = i29 + i3;
                                i25 = i31;
                                i26 = i30;
                                i27 = i18;
                                if (i30 < 8) {
                                    int i32 = 0;
                                    int i33 = i18;
                                    while (true) {
                                        i29 = i31;
                                        i28 = i30;
                                        i18 = i33;
                                        if (i32 < 8) {
                                            i33 += bArr[i31 + i32] & 255;
                                            i32++;
                                        }
                                    }
                                }
                            }
                        }
                        i17 = i26 + 1;
                        i15 = i25 + i3;
                        i18 = i27;
                    }
                    int i34 = i18 >> 6;
                    if (i19 - i16 <= 24) {
                        int i35 = i16 / 2;
                        i34 = i35;
                        if (i8 > 0) {
                            i34 = i35;
                            if (i12 > 0) {
                                int i36 = i8 - 1;
                                int i37 = iArr[i36][i12];
                                int[] iArr2 = iArr[i8];
                                int i38 = i12 - 1;
                                int i39 = ((i37 + (iArr2[i38] * 2)) + iArr[i36][i38]) / 4;
                                i34 = i35;
                                if (i16 < i39) {
                                    i34 = i39;
                                }
                            }
                        }
                    }
                    iArr[i8][i12] = i34;
                    i11 = i12 + 1;
                }
            }
            i7 = i8 + 1;
        }
    }

    private static void calculateThresholdForBlock(byte[] bArr, int i, int i2, int i3, int i4, int[][] iArr, BitMatrix bitMatrix) {
        int i5 = i4 - 8;
        int i6 = i3 - 8;
        int i7 = 0;
        while (true) {
            int i8 = i7;
            if (i8 >= i2) {
                return;
            }
            int i9 = i8 << 3;
            if (i9 > i5) {
                i9 = i5;
            }
            int cap = cap(i8, i2 - 3);
            int i10 = 0;
            while (true) {
                int i11 = i10;
                if (i11 < i) {
                    int i12 = i11 << 3;
                    if (i12 > i6) {
                        i12 = i6;
                    }
                    int cap2 = cap(i11, i - 3);
                    int i13 = 0;
                    for (int i14 = -2; i14 <= 2; i14++) {
                        int[] iArr2 = iArr[cap + i14];
                        i13 += iArr2[cap2 - 2] + iArr2[cap2 - 1] + iArr2[cap2] + iArr2[cap2 + 1] + iArr2[2 + cap2];
                    }
                    thresholdBlock(bArr, i12, i9, i13 / 25, i3, bitMatrix);
                    i10 = i11 + 1;
                }
            }
            i7 = i8 + 1;
        }
    }

    private static int cap(int i, int i2) {
        if (i < 2) {
            return 2;
        }
        return Math.min(i, i2);
    }

    private static void thresholdBlock(byte[] bArr, int i, int i2, int i3, int i4, BitMatrix bitMatrix) {
        int i5 = (i2 * i4) + i;
        int i6 = 0;
        while (i6 < 8) {
            int i7 = 0;
            while (true) {
                int i8 = i7;
                if (i8 < 8) {
                    if ((bArr[i5 + i8] & 255) <= i3) {
                        bitMatrix.set(i + i8, i2 + i6);
                    }
                    i7 = i8 + 1;
                }
            }
            i6++;
            i5 += i4;
        }
    }

    @Override // com.sobot.chat.widget.zxing.common.GlobalHistogramBinarizer, com.sobot.chat.widget.zxing.Binarizer
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    @Override // com.sobot.chat.widget.zxing.common.GlobalHistogramBinarizer, com.sobot.chat.widget.zxing.Binarizer
    public BitMatrix getBlackMatrix() throws NotFoundException {
        BitMatrix bitMatrix = this.matrix;
        if (bitMatrix != null) {
            return bitMatrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < 40 || height < 40) {
            this.matrix = super.getBlackMatrix();
        } else {
            byte[] matrix = luminanceSource.getMatrix();
            int i = width >> 3;
            int i2 = i;
            if ((width & 7) != 0) {
                i2 = i + 1;
            }
            int i3 = height >> 3;
            int i4 = i3;
            if ((height & 7) != 0) {
                i4 = i3 + 1;
            }
            int[][] calculateBlackPoints = calculateBlackPoints(matrix, i2, i4, width, height);
            BitMatrix bitMatrix2 = new BitMatrix(width, height);
            calculateThresholdForBlock(matrix, i2, i4, width, height, calculateBlackPoints, bitMatrix2);
            this.matrix = bitMatrix2;
        }
        return this.matrix;
    }
}
