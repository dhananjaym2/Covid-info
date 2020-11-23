package covid.info;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import covid.info.data.Sender;
import covid.info.list.adapter.ChatConversationAdapter;
import covid.info.list.data.ChatMessage;
import java.util.ArrayList;

public class ChatConversationActivity extends AppCompatActivity implements View.OnClickListener {

  private EditText chatMsgFromUser;
  private RecyclerView recyclerViewChatConversation;

  private final String logTag = ChatConversationActivity.class.getSimpleName();
  private final String chatMessagesConversationHistory = "chatMessagesConversationHistory";
  private ChatConversationAdapter adapter;
  private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
  private ChatConversationViewModel chatConversationViewModel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews(savedInstanceState);

    chatConversationViewModel.getCountriesDataIfNotAvailable();
  }

  private void initViews(Bundle savedInstanceState) {
    chatMsgFromUser = findViewById(R.id.editTextChatMsgFromUser);

    recyclerViewChatConversation = findViewById(R.id.recyclerViewChatConversation);

    if (savedInstanceState != null) {
      chatMessages = savedInstanceState.getParcelableArrayList(chatMessagesConversationHistory);
    }
    adapter = new ChatConversationAdapter(this, chatMessages);
    // set adapter of recycler
    recyclerViewChatConversation.setLayoutManager(new LinearLayoutManager(this));
    recyclerViewChatConversation.setAdapter(adapter);

    ImageButton buttonSend = findViewById(R.id.buttonSend);
    buttonSend.setOnClickListener(this);

    // init ViewModel
    chatConversationViewModel = new ChatConversationViewModel(getApplication());

    //register observer for chat messages
    chatConversationViewModel.getChatMessageLiveData().observe(this, new Observer<ChatMessage>() {
      @Override public void onChanged(ChatMessage chatMessage) {
        addMsgToConversation(chatMessage);
      }
    });
  }

  @Override public void onClick(View view) {
    if (view.getId() == R.id.buttonSend) {
      if (chatMsgFromUser.getText().toString().trim().length() > 0) {
        addMsgToConversation(new ChatMessage(chatMsgFromUser.getText().toString(),
            Sender.mobileAppUser));
        chatConversationViewModel.processUserMessage(chatMsgFromUser.getText().toString());
        // clear the last message sent by user
        chatMsgFromUser.setText("");
      } else {
        addMsgToConversation(
            new ChatMessage(getString(R.string.userRequestIsNotInProperFormat),
                Sender.appValidation));
      }
    }
  }

  @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    // TO save for reuse the chat conversation history
    outState.putParcelableArrayList(chatMessagesConversationHistory, chatMessages);
  }

  private void addMsgToConversation(ChatMessage chatMessage) {
    if (adapter != null) {
      // notify new data has been added.
      int currentListSize = chatMessages.size();
      chatMessages.add(chatMessage);
      adapter.notifyItemInserted(currentListSize);
      recyclerViewChatConversation.smoothScrollToPosition(currentListSize);
    }
  }
}