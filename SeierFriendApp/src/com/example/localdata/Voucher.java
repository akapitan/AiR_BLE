package com.example.localdata;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by vlazar on 2.12.2014..
 */
@Table(name="Voucher")
public class Voucher extends Model {

    @Column(name = "idVoucher")
    private long idVoucher;
    @Column(name = "type")
    private int type;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "reedemable")
    private boolean reedemable;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "voucherTitle")
    private String voucherTitle;
    @Column(name = "voucherTerms")
    private String voucherTerms;
    @Column(name = "voucherLogoUrl")
    private String voucherLogoUrl;
    @Column(name = "voucherImageUrl")
    private String voucherImageUrl;
    @Column(name = "voucherEan")
    private int voucherEan;
    @Column(name = "voucherStoreName")
    private String voucherStoreName;
    @Column(name = "voucherStoreLocation")
    private String voucherStoreLocation;
    @Column(name = "voucherValidUntil")
    private String voucherValidUntil;
    @Column(name = "voucherDiscountStampUrl")
    private String voucherDiscountStampUrl;
    @Column(name = "validFor")
    private int validFor;
    @Column(name = "validFrom")
    private Date validFrom;
    @Column(name = "validUntil")
    private Date validUntil;
    @Column(name = "multipleRedeemable")
    private boolean multipleRedeemable;
    @Column(name = "vestingPeriod")
    private int vestingPeriod;
    @Column(name = "nonRedeemableReason")
    private int nonRedeemableReason;

    public Voucher() {
        super();
    }

    public Voucher(long idVoucher, int type, String name, String description,
                   boolean reedemable, String imageUrl, String voucherTitle,
                   String voucherTerms, String voucherLogoUrl, String voucherImageUrl,
                   int voucherEan, String voucherStoreName, String voucherStoreLocation,
                   String voucherValidUntil, String voucherDiscountStampUrl,
                   int validFor, Date validFrom, Date validUntil,
                   boolean multipleRedeemable, int vestingPeriod,
                   int nonRedeemableReason)
    {
        super();
        this.idVoucher = idVoucher;
        this.type = type;
        this.name = name;
        this.description = description;
        this.reedemable = reedemable;
        this.imageUrl = imageUrl;
        this.voucherTitle = voucherTitle;
        this.voucherTerms = voucherTerms;
        this.voucherLogoUrl = voucherLogoUrl;
        this.voucherImageUrl = voucherImageUrl;
        this.voucherEan = voucherEan;
        this.voucherStoreName = voucherStoreName;
        this.voucherStoreLocation = voucherStoreLocation;
        this.voucherValidUntil = voucherValidUntil;
        this.voucherDiscountStampUrl = voucherDiscountStampUrl;
        this.validFor = validFor;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.multipleRedeemable = multipleRedeemable;
        this.vestingPeriod = vestingPeriod;
        this.nonRedeemableReason = nonRedeemableReason;
    }

    public long getIdVoucher() {
        return idVoucher;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReedemable() {
        return reedemable;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVoucherTitle() {
        return voucherTitle;
    }

    public String getVoucherTerms() {
        return voucherTerms;
    }

    public String getVoucherLogoUrl() {
        return voucherLogoUrl;
    }

    public String getVoucherImageUrl() {
        return voucherImageUrl;
    }

    public int getVoucherEan() {
        return voucherEan;
    }

    public String getVoucherStoreName() {
        return voucherStoreName;
    }

    public String getVoucherStoreLocation() {
        return voucherStoreLocation;
    }

    public String getVoucherValidUntil() {
        return voucherValidUntil;
    }

    public String getVoucherDiscountStampUrl() {
        return voucherDiscountStampUrl;
    }

    public int getValidFor() {
        return validFor;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public boolean isMultipleRedeemable() {
        return multipleRedeemable;
    }

    public int getVestingPeriod() {
        return vestingPeriod;
    }

    public int getNonRedeemableReason() {
        return nonRedeemableReason;
    }
}
