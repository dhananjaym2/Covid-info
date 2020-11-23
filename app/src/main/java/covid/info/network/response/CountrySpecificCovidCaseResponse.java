package covid.info.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CountrySpecificCovidCaseResponse {

  @SerializedName("nbHits")
  @Expose
  private Integer nbHits;
  @SerializedName("page")
  @Expose
  private Integer page;
  @SerializedName("nbPages")
  @Expose
  private Integer nbPages;
  @SerializedName("hitsPerPage")
  @Expose
  private Integer hitsPerPage;
  @SerializedName("exhaustiveNbHits")
  @Expose
  private Boolean exhaustiveNbHits;
  @SerializedName("query")
  @Expose
  private String query;
  @SerializedName("params")
  @Expose
  private String params;
  @SerializedName("processingTimeMS")
  @Expose
  private Integer processingTimeMS;

  public Integer getNbHits() {
    return nbHits;
  }

  public void setNbHits(Integer nbHits) {
    this.nbHits = nbHits;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getNbPages() {
    return nbPages;
  }

  public void setNbPages(Integer nbPages) {
    this.nbPages = nbPages;
  }

  public Integer getHitsPerPage() {
    return hitsPerPage;
  }

  public void setHitsPerPage(Integer hitsPerPage) {
    this.hitsPerPage = hitsPerPage;
  }

  public Boolean getExhaustiveNbHits() {
    return exhaustiveNbHits;
  }

  public void setExhaustiveNbHits(Boolean exhaustiveNbHits) {
    this.exhaustiveNbHits = exhaustiveNbHits;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public Integer getProcessingTimeMS() {
    return processingTimeMS;
  }

  public void setProcessingTimeMS(Integer processingTimeMS) {
    this.processingTimeMS = processingTimeMS;
  }
}