package covid.info;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import covid.info.data.CaseRepository;
import covid.info.data.Sender;
import covid.info.list.data.ChatMessage;
import covid.info.utils.NetworkUtils;

import static covid.info.data.CaseTypeConstants.cases;
import static covid.info.data.CaseTypeConstants.deaths;

class ChatConversationViewModel extends AndroidViewModel {

  private CaseRepository caseRepository;
  private MutableLiveData<ChatMessage> chatMessageLiveData;
  private final String logTag = ChatConversationViewModel.class.getSimpleName();

  ChatConversationViewModel(@NonNull Application application) {
    super(application);
    caseRepository = new CaseRepository(application);
    chatMessageLiveData = caseRepository.getChatMessageLiveData();
  }

  LiveData<ChatMessage> getChatMessageLiveData() {
    return chatMessageLiveData;
  }

  void processUserMessage(String message) {
    // sample valid message: "deaths in india"
    // trim leading and trailing white spaces
    message = message.trim();
    // find first occurrence of space
    int endIndexOfCaseType = message.indexOf(' ');
    if (endIndexOfCaseType <= 0) {
      postChatMessageOnBehalfOfAppValidation(getApplication().getBaseContext().getString(R.string.
          userRequestIsNotInProperFormat));
      return;
    }

    String caseType = message.substring(0, endIndexOfCaseType);
    if (!(caseType.equalsIgnoreCase(cases) || caseType.equalsIgnoreCase(deaths))) {
      //Log.e(logTag, getApplication().getBaseContext().getString(R.string.invalidCaseType));
      postChatMessageOnBehalfOfAppValidation(getApplication().getBaseContext().getString(R.string.
          invalidCaseType));
      return;
    }

    String separatorIn = " in ";
    int startIndexOfSeparator = message.indexOf(separatorIn);
    if (startIndexOfSeparator == -1) {
      postChatMessageOnBehalfOfAppValidation(getApplication().getBaseContext().getString(R.string.
          separatorInNotFound));
      return;
    }

    int endIndexOfSeparator = startIndexOfSeparator + separatorIn.length();
    if (endIndexOfSeparator == message.length()) {
      //Log.e(logTag, "Empty Country"));
      postChatMessageOnBehalfOfAppValidation(getApplication().getBaseContext().getString(R.string.
          userRequestIsNotInProperFormat));
      return;
    }

    String country = message.substring(endIndexOfSeparator);
    caseRepository.getCountrySpecificCaseCount(caseType, country, isConnectedToInternet());
  }

  private void postChatMessageOnBehalfOfAppValidation(String message) {
    Log.i(logTag, "postChatMessageOnBehalfOfAppValidation(): " + message);
    chatMessageLiveData.postValue(new ChatMessage(message, Sender.appValidation));
  }

  void getCountriesDataIfNotAvailable() {
    caseRepository.getCountriesDataIfNotAvailable(null, null, isConnectedToInternet());
  }

  private boolean isConnectedToInternet() {
    return NetworkUtils.isConnectedToInternet(getApplication());
  }
}