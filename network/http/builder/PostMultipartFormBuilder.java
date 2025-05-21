package com.sobot.network.http.builder;

import com.sobot.network.http.request.PostMultipartFormRequest;
import com.sobot.network.http.request.ProgressRequestBody;
import com.sobot.network.http.request.RequestCall;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: source-8303388-dex2jar.jar:com/sobot/network/http/builder/PostMultipartFormBuilder.class */
public class PostMultipartFormBuilder extends OkHttpRequestBuilder implements HasParamsable {
    private List<FileInput> files = new ArrayList();
    private ProgressRequestBody.UploadInterceptor uploadInterceptor;

    /* loaded from: source-8303388-dex2jar.jar:com/sobot/network/http/builder/PostMultipartFormBuilder$FileInput.class */
    public static class FileInput {
        public File file;
        public String filename;
        public String key;

        public FileInput(String str, String str2, File file) {
            this.key = str;
            this.filename = str2;
            this.file = file;
        }

        public String toString() {
            return "FileInput{key='" + this.key + "', filename='" + this.filename + "', file=" + this.file + '}';
        }
    }

    public PostMultipartFormBuilder addFile(String str, String str2, File file) {
        this.files.add(new FileInput(str, str2, file));
        return this;
    }

    @Override // com.sobot.network.http.builder.OkHttpRequestBuilder
    public PostMultipartFormBuilder addHeader(String str, String str2) {
        if (this.headers == null) {
            this.headers = new LinkedHashMap();
        }
        this.headers.put(str, str2);
        return this;
    }

    @Override // com.sobot.network.http.builder.HasParamsable
    public PostMultipartFormBuilder addParams(String str, String str2) {
        if (this.params == null) {
            this.params = new LinkedHashMap();
        }
        this.params.put(str, str2);
        return this;
    }

    @Override // com.sobot.network.http.builder.OkHttpRequestBuilder
    public RequestCall build() {
        return new PostMultipartFormRequest(this.url, this.tag, this.params, this.headers, this.files).uploadInterceptor(this.uploadInterceptor).build();
    }

    @Override // com.sobot.network.http.builder.OkHttpRequestBuilder
    public /* bridge */ /* synthetic */ OkHttpRequestBuilder headers(Map map) {
        return headers((Map<String, String>) map);
    }

    @Override // com.sobot.network.http.builder.OkHttpRequestBuilder
    public PostMultipartFormBuilder headers(Map<String, String> map) {
        this.headers = map;
        return this;
    }

    @Override // com.sobot.network.http.builder.HasParamsable
    public /* bridge */ /* synthetic */ OkHttpRequestBuilder params(Map map) {
        return params((Map<String, String>) map);
    }

    @Override // com.sobot.network.http.builder.HasParamsable
    public PostMultipartFormBuilder params(Map<String, String> map) {
        this.params = map;
        return this;
    }

    @Override // com.sobot.network.http.builder.OkHttpRequestBuilder
    public PostMultipartFormBuilder tag(Object obj) {
        this.tag = obj;
        return this;
    }

    public PostMultipartFormBuilder uploadInterceptor(ProgressRequestBody.UploadInterceptor uploadInterceptor) {
        this.uploadInterceptor = uploadInterceptor;
        return this;
    }

    @Override // com.sobot.network.http.builder.OkHttpRequestBuilder
    public PostMultipartFormBuilder url(String str) {
        this.url = str;
        return this;
    }
}
