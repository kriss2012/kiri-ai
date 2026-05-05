package com.kiriai.kiriorganization.data.models;

/**
 * STABILITY_NOTICE: All fields MUST have @SerializedName for R8/ProGuard compatibility.
 * Use 'private val _field: Any?' pattern for types that might vary between environments.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0016\b\u0087\b\u0018\u00002\u00020\u0001B}\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0001\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0001\u00a2\u0006\u0002\u0010\rJ\u000b\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00c2\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00c2\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00c2\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\u0001H\u00c2\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00c2\u0003J\u0081\u0001\u0010*\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00c6\u0001J\u0013\u0010+\u001a\u00020\u00192\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020\u0013H\u00d6\u0001J\t\u0010.\u001a\u00020\u0003H\u00d6\u0001R\u0012\u0010\f\u001a\u0004\u0018\u00010\u00018\u0002X\u0083\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00018\u0002X\u0083\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00018\u0002X\u0083\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u0004\u0018\u00010\u00018\u0002X\u0083\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u0004\u0018\u00010\u00018\u0002X\u0083\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0012\u001a\u00020\u00138F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000fR\u0011\u0010\u0018\u001a\u00020\u00198F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u00198F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001aR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000fR\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u000fR\u0011\u0010\u001e\u001a\u00020\u00138F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u0015\u00a8\u0006/"}, d2 = {"Lcom/kiriai/kiriorganization/data/models/User;", "", "id", "", "name", "email", "plan", "_dailyRequests", "_totalRequests", "_isVerified", "avatar", "_isPremium", "_dailyLimit", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", "getAvatar", "()Ljava/lang/String;", "dailyLimit", "getDailyLimit", "dailyRequests", "", "getDailyRequests", "()I", "getEmail", "getId", "isPremium", "", "()Z", "isVerified", "getName", "getPlan", "totalRequests", "getTotalRequests", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_release"})
public final class User {
    @com.google.gson.annotations.SerializedName(value = "_id", alternate = {"id"})
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String id = null;
    @com.google.gson.annotations.SerializedName(value = "name")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String name = null;
    @com.google.gson.annotations.SerializedName(value = "email")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String email = null;
    @com.google.gson.annotations.SerializedName(value = "plan")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String plan = null;
    @com.google.gson.annotations.SerializedName(value = "dailyRequests")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object _dailyRequests = null;
    @com.google.gson.annotations.SerializedName(value = "totalRequests")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object _totalRequests = null;
    @com.google.gson.annotations.SerializedName(value = "isVerified")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object _isVerified = null;
    @com.google.gson.annotations.SerializedName(value = "avatar")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String avatar = null;
    @com.google.gson.annotations.SerializedName(value = "isPremium")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object _isPremium = null;
    @com.google.gson.annotations.SerializedName(value = "dailyLimit")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Object _dailyLimit = null;
    
    public User(@org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String email, @org.jetbrains.annotations.Nullable()
    java.lang.String plan, @org.jetbrains.annotations.Nullable()
    java.lang.Object _dailyRequests, @org.jetbrains.annotations.Nullable()
    java.lang.Object _totalRequests, @org.jetbrains.annotations.Nullable()
    java.lang.Object _isVerified, @org.jetbrains.annotations.Nullable()
    java.lang.String avatar, @org.jetbrains.annotations.Nullable()
    java.lang.Object _isPremium, @org.jetbrains.annotations.Nullable()
    java.lang.Object _dailyLimit) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPlan() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAvatar() {
        return null;
    }
    
    public final int getDailyRequests() {
        return 0;
    }
    
    public final int getTotalRequests() {
        return 0;
    }
    
    public final boolean isVerified() {
        return false;
    }
    
    public final boolean isPremium() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDailyLimit() {
        return null;
    }
    
    public User() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    private final java.lang.Object component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    private final java.lang.Object component5() {
        return null;
    }
    
    private final java.lang.Object component6() {
        return null;
    }
    
    private final java.lang.Object component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    private final java.lang.Object component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.kiriai.kiriorganization.data.models.User copy(@org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String email, @org.jetbrains.annotations.Nullable()
    java.lang.String plan, @org.jetbrains.annotations.Nullable()
    java.lang.Object _dailyRequests, @org.jetbrains.annotations.Nullable()
    java.lang.Object _totalRequests, @org.jetbrains.annotations.Nullable()
    java.lang.Object _isVerified, @org.jetbrains.annotations.Nullable()
    java.lang.String avatar, @org.jetbrains.annotations.Nullable()
    java.lang.Object _isPremium, @org.jetbrains.annotations.Nullable()
    java.lang.Object _dailyLimit) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}