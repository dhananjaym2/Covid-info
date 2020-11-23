package covid.info.list.adapter;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import covid.info.R;
import covid.info.list.data.ChatMessage;
import java.util.List;

public class ChatConversationAdapter
    extends RecyclerView.Adapter<ChatConversationAdapter.ListItemViewHolder> {

  private Activity activity;
  private List<ChatMessage> chatMessages;

  public ChatConversationAdapter(Activity activity, List<ChatMessage> chatMessages) {
    this.activity = activity;
    this.chatMessages = chatMessages;
  }

  @NonNull @Override public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item,
        parent, false);
    return new ListItemViewHolder(itemView);
  }

  @Override public void onBindViewHolder(@NonNull ListItemViewHolder holder, final int position) {

    String messageToShow = chatMessages.get(position).getMessage();
    // to show user friendly messages for ErrorCodeConstants
    switch (messageToShow) {
      case "countryNotFoundInLocalDb":
        messageToShow = activity.getString(R.string.invalidCountry);
        break;

      case "caseTypeNotSupported":
        messageToShow = activity.getString(R.string.invalidCaseType);
        break;

      case "internetNotConnected":
        messageToShow = activity.getString(R.string.internetNotConnected);
        break;

      case "networkError":
        messageToShow = activity.getString(R.string.networkError);
        break;
    }
    holder.textViewChatMsg.setText(messageToShow);

    int backgroundColor, textGravity = Gravity.START, leftPadding = 15, rightPadding = 40;
    switch (chatMessages.get(position).getMsgSentByUser()) {

      case mobileAppUser:
        textGravity = Gravity.END;
        backgroundColor = R.color.black;
        leftPadding = 40;
        rightPadding = 15;
        break;

      case server:
        backgroundColor = R.color.colorAccent;
        break;

      case localDatabase:
        backgroundColor = R.color.grayColorMayBeOutdatedInfo;
        break;

      case appValidation:
        backgroundColor = R.color.orangeColorWarning;
        break;

      default:
        backgroundColor = R.color.white;
        break;
    }
    holder.textViewChatMsg.setPadding(leftPadding, 10, rightPadding, 10);
    holder.textViewChatMsg.setGravity(textGravity);
    holder.textViewChatMsg.setTextColor(backgroundColor);
  }

  @Override public int getItemCount() {
    if (chatMessages == null) {
      return 0;
    } else {
      return chatMessages.size();
    }
  }

  static class ListItemViewHolder extends RecyclerView.ViewHolder {
    TextView textViewChatMsg;

    ListItemViewHolder(View itemView) {
      super(itemView);
      textViewChatMsg = itemView.findViewById(R.id.textViewChatMsg);
    }
  }
}