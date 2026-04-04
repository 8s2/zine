package com.eightsidedsquare.zine.client.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public enum ConnectedPattern {
    AAAA("all", "aaaa"),
    AHHA("ahha"),
    HHHH("horizontal", "hhhh"),
    HAAH("haah"),
    AHCV("ahcv"),
    HAVC("havc"),
    VCCV("vccv"),
    HHCC("hhcc"),
    CNCC("cncc"),
    CCNC("ccnc"),
    NCCN("nccn"),
    NNCC("nncc"),
    AAVV("aavv"),
    AHNV("ahnv"),
    HHNN("hhnn"),
    HAVN("havn"),
    VCHA("vcha"),
    CVAH("cvah"),
    CCHH("cchh"),
    CVVC("cvvc"),
    NCCC("nccc"),
    CCCN("cccn"),
    CCNN("ccnn"),
    CNNC("cnnc"),
    VVVV("vertical", "vvvv"),
    VNNV("vnnv"),
    NNNN("none", "nnnn"),
    NVVN("nvvn"),
    VCNV("vcnv"),
    HHCN("hhcn"),
    VNCV("vncv"),
    HHNC("hhnc"),
    NNCN("nncn"),
    NNNC("nnnc"),
    CNCN("cncn"),
    NCNC("ncnc"),
    VVAA("vvaa"),
    VNHA("vnha"),
    NNHH("nnhh"),
    NVAH("nvah"),
    CNHH("cnhh"),
    NVVC("nvvc"),
    NCHH("nchh"),
    CVVN("cvvn"),
    NCNN("ncnn"),
    CNNN("cnnn"),
    CCCC("corners", "cccc");

    private static final Int2ObjectMap<ConnectedPattern> LOOKUP = createLookup();

    private final String suffix;
    private final ConnectedShape nw;
    private final ConnectedShape ne;
    private final ConnectedShape se;
    private final ConnectedShape sw;

    ConnectedPattern(String suffix, ConnectedShape nw, ConnectedShape ne, ConnectedShape se, ConnectedShape sw) {
        this.suffix = "_" + suffix;
        this.nw = nw;
        this.ne = ne;
        this.se = se;
        this.sw = sw;
    }

    ConnectedPattern(String suffix, String name) {
        this(
                suffix,
                ConnectedShape.fromChar(name.charAt(0)),
                ConnectedShape.fromChar(name.charAt(1)),
                ConnectedShape.fromChar(name.charAt(2)),
                ConnectedShape.fromChar(name.charAt(3))
        );
    }

    ConnectedPattern(String name) {
        this(name, name);
    }

    private static int getLookupId(ConnectedShape nw, ConnectedShape ne, ConnectedShape se, ConnectedShape sw) {
        return nw.ordinal() + ne.ordinal() * 5 + se.ordinal() * 25 + sw.ordinal() * 125;
    }

    private static Int2ObjectMap<ConnectedPattern> createLookup() {
        Int2ObjectMap<ConnectedPattern> lookup = new Int2ObjectArrayMap<>();
        for (ConnectedPattern pattern : ConnectedPattern.values()) {
            lookup.put(getLookupId(pattern.nw, pattern.ne, pattern.se, pattern.sw), pattern);
        }
        return lookup;
    }

    public static ConnectedPattern from(ConnectedShape nw, ConnectedShape ne, ConnectedShape se, ConnectedShape sw) {
        return LOOKUP.getOrDefault(getLookupId(nw, ne, se, sw), AAAA);
    }

    public boolean contains(ConnectedShape shape) {
        return this.nw == shape || this.ne == shape || this.se == shape || this.sw == shape;
    }

    public boolean allMatch() {
        return this.nw == this.ne && this.ne == this.se && this.se == this.sw;
    }

    public Identifier addSuffix(Identifier id) {
        return id.withSuffix(this.suffix);
    }

    public ConnectedShape getNW() {
        return this.nw;
    }

    public ConnectedShape getNE() {
        return this.ne;
    }

    public ConnectedShape getSE() {
        return this.se;
    }

    public ConnectedShape getSW() {
        return this.sw;
    }

    public ConnectedPattern and(ConnectedPattern pattern) {
        return this == pattern ? this : from(
                this.nw.and(pattern.nw),
                this.ne.and(pattern.ne),
                this.se.and(pattern.se),
                this.sw.and(pattern.sw)
        );
    }

    public ConnectedPattern or(ConnectedPattern pattern) {
        return this == pattern ? this : from(
                this.nw.or(pattern.nw),
                this.ne.or(pattern.ne),
                this.se.or(pattern.se),
                this.sw.or(pattern.sw)
        );
    }
}
