package com.example.appication.ui.ViewAdaptor;

public class MailViewItem extends BaseItem {

    private int MailImageView;
    private String MailTitleView;
    private String MailTextView;

    public MailViewItem() {
        super(Viewtype.TYPE_MAIL_VIEW);
    }

    public int getMailImageView() {
        return MailImageView;
    }

    public MailViewItem setMailImageView(int mailImageView) {
        MailImageView = mailImageView;
        return this;
    }

    public String getMailTitleView() {
        return MailTitleView;
    }

    public MailViewItem setMailTitleView(String mailTitleView) {
        MailTitleView = mailTitleView;
        return this;
    }

    public String getMailTextView() {
        return MailTextView;
    }

    public MailViewItem setMailTextView(String mailTextView) {
        MailTextView = mailTextView;
        return this;
    }
}
