package covid.info.list.data;

import android.os.Parcel;
import android.os.Parcelable;
import covid.info.data.Sender;

public class ChatMessage implements Parcelable {

  private String message;
  private Sender msgSentByUser;

  public ChatMessage(String message, Sender msgSentByUser) {
    this.message = message;
    this.msgSentByUser = msgSentByUser;
  }

  protected ChatMessage(Parcel in) {
    message = in.readString();
    msgSentByUser = Sender.values()[in.readInt()];
  }

  public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
    @Override
    public ChatMessage createFromParcel(Parcel in) {
      return new ChatMessage(in);
    }

    @Override
    public ChatMessage[] newArray(int size) {
      return new ChatMessage[size];
    }
  };

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Sender getMsgSentByUser() {
    return msgSentByUser;
  }

  public void setMsgSentByUser(Sender msgSentByUser) {
    this.msgSentByUser = msgSentByUser;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(message);
    dest.writeInt(msgSentByUser.ordinal());
  }
}