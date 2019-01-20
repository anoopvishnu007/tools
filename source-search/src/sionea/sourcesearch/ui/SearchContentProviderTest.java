package sionea.sourcesearch.ui;

import org.eclipse.jface.viewers.Viewer;

import sionea.sourcesearch.data.SearchData;
import sionea.sourcesearch.data.SearchResult;

public class SearchContentProviderTest extends SearchContentProvider {

	private SearchResult[] list;
	private TestSourceRec[] allSources;
	
	class TestSourceRec {
		private int sourceId;
		private String sourceName;
		private String sourceType;
		private String content;
		public TestSourceRec(int sourceId, String sourceName, String sourceType, String content) {
			this.sourceName = sourceName;
			this.sourceType = sourceType;
			this.content = content;
			this.sourceId = sourceId;
		}
		public String getSourceName() {
			return sourceName;
		}
		public void setSourceName(String sourceName) {
			this.sourceName = sourceName;
		}
		public String getSourceType() {
			return sourceType;
		}
		public void setSourceType(String sourceType) {
			this.sourceType = sourceType;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public int getSourceId() {
			return sourceId;
		}
		public void setSourceId(int sourceId) {
			this.sourceId = sourceId;
		}
	}

	public SearchContentProviderTest() {
		this.list = new SearchResult[0];
		this.allSources = new TestSourceRec[]{
				new TestSourceRec(1, "amazon_aws_s3_pkg", "PACKAGE BODY", "create or replace package body amazon_aws_s3_pkg\r\n" + 
						"as\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   PL/SQL wrapper package for Amazon AWS S3 API\r\n" + 
						"\r\n" + 
						"  Remarks:   inspired by the whitepaper \"Building an Amazon S3 Client with Application Express 4.0\" by Jason Straub\r\n" + 
						"             see http://jastraub.blogspot.com/2011/01/building-amazon-s3-client-with.html\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     09.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  g_aws_url_s3             constant varchar2(255) := 'http://s3.amazonaws.com/';\r\n" + 
						"  g_aws_host_s3            constant varchar2(255) := 's3.amazonaws.com';\r\n" + 
						"  g_aws_namespace_s3       constant varchar2(255) := 'http://s3.amazonaws.com/doc/2006-03-01/';\r\n" + 
						"  g_aws_namespace_s3_full  constant varchar2(255) := 'xmlns=\"' || g_aws_namespace_s3 || '\"';\r\n" + 
						"\r\n" + 
						"  g_date_format_xml        constant varchar2(30) := 'YYYY-MM-DD\"T\"HH24:MI:SS\".000Z\"';\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"procedure raise_error (p_error_message in varchar2)\r\n" + 
						"as\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   raise error\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     15.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  raise_application_error (-20000, p_error_message);\r\n" + 
						"\r\n" + 
						"end raise_error;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"procedure check_for_errors (p_clob in clob)\r\n" + 
						"as\r\n" + 
						"  l_xml xmltype;\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   check for errors (clob)\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     15.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  if (p_clob is not null) and (length(p_clob) > 0) then\r\n" + 
						"\r\n" + 
						"    l_xml := xmltype (p_clob);\r\n" + 
						"\r\n" + 
						"    if l_xml.existsnode('/Error') = 1 then\r\n" + 
						"      debug_pkg.print (l_xml);\r\n" + 
						"      raise_error (l_xml.extract('/Error/Message/text()').getstringval());\r\n" + 
						"    end if;\r\n" + 
						"    \r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"end check_for_errors;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"procedure check_for_errors (p_xml in xmltype)\r\n" + 
						"as\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   check for errors (XMLType)\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     15.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  if p_xml.existsnode('/Error') = 1 then\r\n" + 
						"    debug_pkg.print (p_xml);\r\n" + 
						"    raise_error (p_xml.extract('/Error/Message/text()').getstringval());\r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"end check_for_errors;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function check_for_redirect (p_clob in clob) return varchar2\r\n" + 
						"as\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"  l_returnvalue                  varchar2(4000);\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   check for redirect\r\n" + 
						"\r\n" + 
						"  Remarks:   Used by the \"delete bucket\" procedure, by Jeffrey Kemp\r\n" + 
						"             see http://code.google.com/p/plsql-utils/issues/detail?id=14\r\n" + 
						"             \"One thing I found when testing was that if the bucket is not in the US standard region,\r\n" + 
						"              Amazon seems to respond with a TemporaryRedirect error.\r\n" + 
						"              If the same request is re-requested to the indicated URL it works.\"\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     16.02.2013  Created, based on code by Jeffrey Kemp\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  if (p_clob is not null) and (length(p_clob) > 0) then\r\n" + 
						"\r\n" + 
						"    l_xml := xmltype (p_clob);\r\n" + 
						"\r\n" + 
						"    if l_xml.existsnode('/Error') = 1 then\r\n" + 
						"\r\n" + 
						"      if l_xml.extract('/Error/Code/text()').getStringVal = 'TemporaryRedirect' then\r\n" + 
						"        l_returnvalue := l_xml.extract('/Error/Endpoint/text()').getStringVal;\r\n" + 
						"        debug_pkg.printf('Temporary Redirect to %1', l_returnvalue);\r\n" + 
						"      end if;\r\n" + 
						"\r\n" + 
						"    end if;\r\n" + 
						"\r\n" + 
						"  end if;\r\n" + 
						"  \r\n" + 
						"  return l_returnvalue;\r\n" + 
						"\r\n" + 
						"end check_for_redirect;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function make_request (p_url in varchar2,\r\n" + 
						"                       p_http_method in varchar2,\r\n" + 
						"                       p_header_names in t_str_array,\r\n" + 
						"                       p_header_values in t_str_array,\r\n" + 
						"                       p_request_blob in blob := null,\r\n" + 
						"                       p_request_clob in clob := null) return clob\r\n" + 
						"as\r\n" + 
						"  l_http_req     utl_http.req;\r\n" + 
						"  l_http_resp    utl_http.resp;\r\n" + 
						"\r\n" + 
						"  l_amount       binary_integer := 32000;\r\n" + 
						"  l_offset       integer := 1;\r\n" + 
						"  l_buffer       varchar2(32000);\r\n" + 
						"  l_buffer_raw   raw(32000);\r\n" + 
						"\r\n" + 
						"  l_response     varchar2(2000);\r\n" + 
						"  l_returnvalue  clob;\r\n" + 
						"\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   make HTTP request\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     15.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  debug_pkg.printf('%1 %2', p_http_method, p_url);\r\n" + 
						"\r\n" + 
						"  l_http_req := utl_http.begin_request(p_url, p_http_method);\r\n" + 
						"  \r\n" + 
						"  if p_header_names.count > 0 then\r\n" + 
						"    for i in p_header_names.first .. p_header_names.last loop\r\n" + 
						"      --debug_pkg.printf('%1: %2', p_header_names(i), p_header_values(i));\r\n" + 
						"      utl_http.set_header(l_http_req, p_header_names(i), p_header_values(i));\r\n" + 
						"    end loop;\r\n" + 
						"  end if;\r\n" + 
						"  \r\n" + 
						"  if p_request_blob is not null then\r\n" + 
						"\r\n" + 
						"    begin\r\n" + 
						"      loop\r\n" + 
						"        dbms_lob.read (p_request_blob, l_amount, l_offset, l_buffer_raw);\r\n" + 
						"        utl_http.write_raw (l_http_req, l_buffer_raw);\r\n" + 
						"        l_offset := l_offset + l_amount;\r\n" + 
						"        l_amount := 32000;\r\n" + 
						"      end loop;\r\n" + 
						"    exception\r\n" + 
						"      when no_data_found then\r\n" + 
						"        null;\r\n" + 
						"    end;\r\n" + 
						"\r\n" + 
						"  elsif p_request_clob is not null then\r\n" + 
						"  \r\n" + 
						"    begin\r\n" + 
						"      loop\r\n" + 
						"        dbms_lob.read (p_request_clob, l_amount, l_offset, l_buffer);\r\n" + 
						"        utl_http.write_text (l_http_req, l_buffer);\r\n" + 
						"        l_offset := l_offset + l_amount;\r\n" + 
						"        l_amount := 32000;\r\n" + 
						"      end loop;\r\n" + 
						"    exception\r\n" + 
						"      when no_data_found then\r\n" + 
						"        null;\r\n" + 
						"    end;\r\n" + 
						"\r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"  l_http_resp := utl_http.get_response(l_http_req);\r\n" + 
						"\r\n" + 
						"  dbms_lob.createtemporary (l_returnvalue, false);\r\n" + 
						"  dbms_lob.open (l_returnvalue, dbms_lob.lob_readwrite);\r\n" + 
						"\r\n" + 
						"  begin\r\n" + 
						"    loop\r\n" + 
						"      utl_http.read_text (l_http_resp, l_buffer);\r\n" + 
						"      dbms_lob.writeappend (l_returnvalue, length(l_buffer), l_buffer);\r\n" + 
						"    end loop;\r\n" + 
						"  exception\r\n" + 
						"    when others then\r\n" + 
						"      if sqlcode <> -29266 then\r\n" + 
						"        raise;\r\n" + 
						"      end if;\r\n" + 
						"  end;\r\n" + 
						"\r\n" + 
						"  utl_http.end_response (l_http_resp);\r\n" + 
						"  \r\n" + 
						"  return l_returnvalue;\r\n" + 
						"\r\n" + 
						"end make_request;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_url (p_bucket_name in varchar2,\r\n" + 
						"                  p_key in varchar2 := null) return varchar2\r\n" + 
						"as\r\n" + 
						"  l_returnvalue varchar2(4000);\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   construct a valid URL\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     03.03.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  l_returnvalue := 'http://' || p_bucket_name || '.' || g_aws_host_s3 || '/' || p_key;\r\n" + 
						"  \r\n" + 
						"  return l_returnvalue;\r\n" + 
						"  \r\n" + 
						"end get_url;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_host (p_bucket_name in varchar2) return varchar2\r\n" + 
						"as\r\n" + 
						"  l_returnvalue varchar2(4000);\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   construct a valid host string\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     03.03.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  l_returnvalue := p_bucket_name || '.' || g_aws_host_s3;\r\n" + 
						"  \r\n" + 
						"  return l_returnvalue;\r\n" + 
						"  \r\n" + 
						"end get_host;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_bucket_list return t_bucket_list\r\n" + 
						"as\r\n" + 
						"  l_clob                         clob;\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"\r\n" + 
						"  l_date_str                     varchar2(255);\r\n" + 
						"  l_auth_str                     varchar2(255);\r\n" + 
						"  \r\n" + 
						"  l_header_names                 t_str_array := t_str_array();\r\n" + 
						"  l_header_values                t_str_array := t_str_array();\r\n" + 
						"\r\n" + 
						"  l_count                        pls_integer := 0;\r\n" + 
						"  l_returnvalue                  t_bucket_list;\r\n" + 
						"  \r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get buckets\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     09.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  l_date_str := amazon_aws_auth_pkg.get_date_string;\r\n" + 
						"  l_auth_str := amazon_aws_auth_pkg.get_auth_string ('GET' || chr(10) || chr(10) || chr(10) || l_date_str || chr(10) || '/');\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(1) := 'Host';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(1) := g_aws_host_s3;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(2) := 'Date';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(2) := l_date_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(3) := 'Authorization';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(3) := l_auth_str;\r\n" + 
						"\r\n" + 
						"  l_clob := make_request (g_aws_url_s3, 'GET', l_header_names, l_header_values, null);\r\n" + 
						"\r\n" + 
						"  if (l_clob is not null) and (length(l_clob) > 0) then\r\n" + 
						"  \r\n" + 
						"    l_xml := xmltype (l_clob);\r\n" + 
						"    \r\n" + 
						"    check_for_errors (l_xml);\r\n" + 
						"\r\n" + 
						"    for l_rec in (\r\n" + 
						"      select extractValue(value(t), '*/Name', g_aws_namespace_s3_full) as bucket_name,\r\n" + 
						"        extractValue(value(t), '*/CreationDate', g_aws_namespace_s3_full) as creation_date\r\n" + 
						"      from table(xmlsequence(l_xml.extract('//ListAllMyBucketsResult/Buckets/Bucket', g_aws_namespace_s3_full))) t\r\n" + 
						"      ) loop\r\n" + 
						"      l_count := l_count + 1;\r\n" + 
						"      l_returnvalue(l_count).bucket_name := l_rec.bucket_name;\r\n" + 
						"      l_returnvalue(l_count).creation_date := to_date(l_rec.creation_date, g_date_format_xml);\r\n" + 
						"    end loop;\r\n" + 
						"    \r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"  return l_returnvalue;\r\n" + 
						"\r\n" + 
						"end get_bucket_list;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_bucket_tab return t_bucket_tab pipelined\r\n" + 
						"as\r\n" + 
						"  l_bucket_list                  t_bucket_list;\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get buckets\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     19.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  l_bucket_list := get_bucket_list;\r\n" + 
						"  \r\n" + 
						"  for i in 1 .. l_bucket_list.count loop\r\n" + 
						"    pipe row (l_bucket_list(i));\r\n" + 
						"  end loop;\r\n" + 
						"  \r\n" + 
						"  return;\r\n" + 
						"\r\n" + 
						"end get_bucket_tab;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"procedure new_bucket (p_bucket_name in varchar2,\r\n" + 
						"                      p_region in varchar2 := null)\r\n" + 
						"as\r\n" + 
						"\r\n" + 
						"  l_request_body                 clob;\r\n" + 
						"  l_clob                         clob;\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"\r\n" + 
						"  l_date_str                     varchar2(255);\r\n" + 
						"  l_auth_str                     varchar2(255);\r\n" + 
						"  \r\n" + 
						"  l_header_names                 t_str_array := t_str_array();\r\n" + 
						"  l_header_values                t_str_array := t_str_array();\r\n" + 
						"\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   create bucket\r\n" + 
						"\r\n" + 
						"  Remarks:   *** bucket names must be unique across all of Amazon S3 ***\r\n" + 
						"  \r\n" + 
						"             see http://docs.amazonwebservices.com/AmazonS3/latest/API/RESTBucketPUT.html\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     15.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  l_date_str := amazon_aws_auth_pkg.get_date_string;\r\n" + 
						"\r\n" + 
						"  if p_region is not null then\r\n" + 
						"    l_auth_str := amazon_aws_auth_pkg.get_auth_string ('PUT' || chr(10) || chr(10) || 'text/plain' || chr(10) || l_date_str || chr(10) || '/' || p_bucket_name || '/');\r\n" + 
						"  else\r\n" + 
						"    l_auth_str := amazon_aws_auth_pkg.get_auth_string ('PUT' || chr(10) || chr(10) || chr(10) || l_date_str || chr(10) || '/' || p_bucket_name || '/');\r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(1) := 'Host';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(1) := get_host(p_bucket_name);\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(2) := 'Date';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(2) := l_date_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(3) := 'Authorization';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(3) := l_auth_str;\r\n" + 
						"  \r\n" + 
						"  if p_region is not null then\r\n" + 
						"\r\n" + 
						"    l_request_body := '<CreateBucketConfiguration ' || g_aws_namespace_s3_full || '><LocationConstraint>' || p_region || '</LocationConstraint></CreateBucketConfiguration>';\r\n" + 
						"\r\n" + 
						"    l_header_names.extend;\r\n" + 
						"    l_header_names(4) := 'Content-Type';\r\n" + 
						"    l_header_values.extend;\r\n" + 
						"    l_header_values(4) := 'text/plain';\r\n" + 
						"\r\n" + 
						"    l_header_names.extend;\r\n" + 
						"    l_header_names(5) := 'Content-Length';\r\n" + 
						"    l_header_values.extend;\r\n" + 
						"    l_header_values(5) := length(l_request_body);\r\n" + 
						"\r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"  l_clob := make_request (get_url (p_bucket_name), 'PUT', l_header_names, l_header_values, null, l_request_body);\r\n" + 
						"  \r\n" + 
						"  check_for_errors (l_clob);\r\n" + 
						"\r\n" + 
						"end new_bucket;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_bucket_region (p_bucket_name in varchar2) return varchar2\r\n" + 
						"as\r\n" + 
						"\r\n" + 
						"  l_clob                         clob;\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"\r\n" + 
						"  l_date_str                     varchar2(255);\r\n" + 
						"  l_auth_str                     varchar2(255);\r\n" + 
						"  \r\n" + 
						"  l_header_names                 t_str_array := t_str_array();\r\n" + 
						"  l_header_values                t_str_array := t_str_array();\r\n" + 
						"  \r\n" + 
						"  l_returnvalue                  varchar2(255);\r\n" + 
						"\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get bucket region\r\n" + 
						"\r\n" + 
						"  Remarks:   see http://docs.amazonwebservices.com/AmazonS3/latest/API/RESTBucketGETlocation.html\r\n" + 
						"  \r\n" + 
						"             note that the region will be NULL for buckets in the default region (US)\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     03.03.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  l_date_str := amazon_aws_auth_pkg.get_date_string;\r\n" + 
						"  l_auth_str := amazon_aws_auth_pkg.get_auth_string ('GET' || chr(10) || chr(10) || chr(10) || l_date_str || chr(10) || '/' || p_bucket_name || '/?location');\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(1) := 'Host';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(1) := get_host(p_bucket_name);\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(2) := 'Date';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(2) := l_date_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(3) := 'Authorization';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(3) := l_auth_str;\r\n" + 
						"\r\n" + 
						"  l_clob := make_request (get_url(p_bucket_name) || '?location', 'GET', l_header_names, l_header_values);\r\n" + 
						"\r\n" + 
						"  if (l_clob is not null) and (length(l_clob) > 0) then\r\n" + 
						"  \r\n" + 
						"    l_xml := xmltype (l_clob);\r\n" + 
						"    \r\n" + 
						"    check_for_errors (l_xml);\r\n" + 
						"    \r\n" + 
						"    if l_xml.existsnode('/LocationConstraint', g_aws_namespace_s3_full) = 1 then\r\n" + 
						"      -- see http://pbarut.blogspot.com/2006/11/ora-30625-and-xmltype.html\r\n" + 
						"      if l_xml.extract('/LocationConstraint/text()', g_aws_namespace_s3_full) is not null then\r\n" + 
						"        l_returnvalue := l_xml.extract('/LocationConstraint/text()', g_aws_namespace_s3_full).getstringval();\r\n" + 
						"      else\r\n" + 
						"        l_returnvalue := null;\r\n" + 
						"      end if;\r\n" + 
						"    end if;\r\n" + 
						"    \r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"  return l_returnvalue;\r\n" + 
						"\r\n" + 
						"end get_bucket_region;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"procedure get_object_list (p_bucket_name                 in varchar2,\r\n" + 
						"                           p_prefix                      in varchar2,\r\n" + 
						"                           p_max_keys                    in number,\r\n" + 
						"                           p_list                       out t_object_list,\r\n" + 
						"                           p_next_continuation_token in out varchar2)\r\n" + 
						"as\r\n" + 
						"  l_clob                         clob;\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"  l_xml_is_truncated             xmltype;\r\n" + 
						"  l_xml_next_continuation        xmltype;\r\n" + 
						"\r\n" + 
						"  l_date_str                     varchar2(255);\r\n" + 
						"  l_auth_str                     varchar2(255);\r\n" + 
						"\r\n" + 
						"  l_header_names                 t_str_array := t_str_array();\r\n" + 
						"  l_header_values                t_str_array := t_str_array();\r\n" + 
						"\r\n" + 
						"  l_returnvalue                  t_object_list;\r\n" + 
						"\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get objects\r\n" + 
						"\r\n" + 
						"  Remarks:   see http://docs.aws.amazon.com/AmazonS3/latest/API/v2-RESTBucketGET.html\r\n" + 
						"  \r\n" + 
						"             see http://code.google.com/p/plsql-utils/issues/detail?id=16\r\n" + 
						"  \r\n" + 
						"             \"I've rewritten get_object_list as an internal procedure that uses the \"marker\" parameter,\r\n" + 
						"             so that get_object_tab can now call the Amazon API multiple times to return the complete set of objects.\r\n" + 
						"             The get_object_list function remains functionally unchanged in this version - it just returns one set of objects -\r\n" + 
						"             it could be enhanced to support the marker parameter as well, I guess,\r\n" + 
						"             but I'd rather not expose that sort of thing to the caller personally.\r\n" + 
						"             The nice thing about the pipelined function is that the subsequent calls to Amazon\r\n" + 
						"             will only be executed if the client actually fetches all the rows.\"\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     15.01.2011  Created\r\n" + 
						"  JKEMP   14.08.2012  Rewritten as private procedure, see remarks above\r\n" + 
						"  KJS     06.10.2016  Modified to use newest S3 API which performs much better on large buckets. Changed for-loop to bulk operation.\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  l_date_str := amazon_aws_auth_pkg.get_date_string;\r\n" + 
						"  l_auth_str := amazon_aws_auth_pkg.get_auth_string ('GET' || chr(10) || chr(10) || chr(10) || l_date_str || chr(10) || '/' || p_bucket_name || '/');\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(1) := 'Host';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(1) := get_host (p_bucket_name);\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(2) := 'Date';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(2) := l_date_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(3) := 'Authorization';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(3) := l_auth_str;\r\n" + 
						"\r\n" + 
						"  if p_next_continuation_token is not null then\r\n" + 
						"    l_clob := make_request (get_url(p_bucket_name) || '?list-type=2&continuation-token=' || utl_url.escape(p_next_continuation_token) || '&max-keys=' || p_max_keys || '&prefix=' || utl_url.escape(p_prefix), 'GET', l_header_names, l_header_values, null);\r\n" + 
						"  else\r\n" + 
						"    l_clob := make_request (get_url(p_bucket_name) || '?list-type=2&max-keys=' || p_max_keys || '&prefix=' || utl_url.escape(p_prefix), 'GET', l_header_names, l_header_values, null);\r\n" + 
						"  end if;\r\n" + 
						"  if (l_clob is not null) and (length(l_clob) > 0) then\r\n" + 
						"\r\n" + 
						"    l_xml := xmltype (l_clob);\r\n" + 
						"\r\n" + 
						"    check_for_errors (l_xml);\r\n" + 
						"\r\n" + 
						"    select extractValue(value(t), '*/Key', g_aws_namespace_s3_full),\r\n" + 
						"      extractValue(value(t), '*/Size', g_aws_namespace_s3_full),\r\n" + 
						"      to_date(extractValue(value(t), '*/LastModified', g_aws_namespace_s3_full), g_date_format_xml)\r\n" + 
						"    bulk collect into l_returnvalue\r\n" + 
						"    from table(xmlsequence(l_xml.extract('//ListBucketResult/Contents', g_aws_namespace_s3_full))) t;\r\n" + 
						"      \r\n" + 
						"    -- check if this is the last set of data or not, and set the in/out p_next_continuation_token as expected\r\n" + 
						"    l_xml_is_truncated := l_xml.extract('//ListBucketResult/IsTruncated/text()', g_aws_namespace_s3_full);\r\n" + 
						"    \r\n" + 
						"    if l_xml_is_truncated is not null and l_xml_is_truncated.getStringVal = 'true' then\r\n" + 
						"      l_xml_next_continuation := l_xml.extract('//ListBucketResult/NextContinuationToken/text()', g_aws_namespace_s3_full);\r\n" + 
						"      if l_xml_next_continuation is not null then\r\n" + 
						"        p_next_continuation_token := l_xml_next_continuation.getStringVal;\r\n" + 
						"      else\r\n" + 
						"        p_next_continuation_token := null;\r\n" + 
						"      end if;\r\n" + 
						"    else\r\n" + 
						"      p_next_continuation_token := null;\r\n" + 
						"    end if;\r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"  p_list := l_returnvalue;\r\n" + 
						"\r\n" + 
						"end get_object_list;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_object_list (p_bucket_name in varchar2,\r\n" + 
						"                          p_prefix in varchar2 := null,\r\n" + 
						"                          p_max_keys in number := null) return t_object_list\r\n" + 
						"as\r\n" + 
						"  l_object_list                  t_object_list;\r\n" + 
						"  l_next_continuation_token      varchar2(4000);\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get objects\r\n" + 
						"\r\n" + 
						"  Remarks:   see http://docs.amazonwebservices.com/AmazonS3/latest/API/index.html?RESTObjectGET.html\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  JKEMP   14.08.2012  Created\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  get_object_list (\r\n" + 
						"    p_bucket_name             => p_bucket_name,\r\n" + 
						"    p_prefix                  => p_prefix,\r\n" + 
						"    p_max_keys                => p_max_keys,\r\n" + 
						"    p_list                    => l_object_list,\r\n" + 
						"    p_next_continuation_token => l_next_continuation_token --ignored by this function\r\n" + 
						"  );\r\n" + 
						"\r\n" + 
						"  return l_object_list;\r\n" + 
						"\r\n" + 
						"end get_object_list;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_object_tab (p_bucket_name in varchar2,\r\n" + 
						"                         p_prefix in varchar2 := null,\r\n" + 
						"                         p_max_keys in number := null) return t_object_tab pipelined\r\n" + 
						"as\r\n" + 
						"  l_object_list                  t_object_list;\r\n" + 
						"  l_next_continuation_token           varchar2(4000);\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get objects\r\n" + 
						"\r\n" + 
						"  Remarks:\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     19.01.2011  Created\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  loop\r\n" + 
						"\r\n" + 
						"    get_object_list (\r\n" + 
						"      p_bucket_name             => p_bucket_name,\r\n" + 
						"      p_prefix                  => p_prefix,\r\n" + 
						"      p_max_keys                => p_max_keys,\r\n" + 
						"      p_list                    => l_object_list,\r\n" + 
						"      p_next_continuation_token => l_next_continuation_token\r\n" + 
						"      );\r\n" + 
						"  \r\n" + 
						"    for i in 1 .. l_object_list.count loop\r\n" + 
						"      pipe row (l_object_list(i));\r\n" + 
						"    end loop;\r\n" + 
						"    \r\n" + 
						"    exit when l_next_continuation_token is null;\r\n" + 
						"  \r\n" + 
						"  end loop;\r\n" + 
						"\r\n" + 
						"  return;\r\n" + 
						"\r\n" + 
						"end get_object_tab;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_download_url (p_bucket_name in varchar2,\r\n" + 
						"                           p_key in varchar2,\r\n" + 
						"                           p_expiry_date in date) return varchar2\r\n" + 
						"as\r\n" + 
						"  l_returnvalue                  varchar2(4000);\r\n" + 
						"  l_key                          varchar2(4000) := utl_url.escape (p_key);\r\n" + 
						"  l_epoch                        number;\r\n" + 
						"  l_signature                    varchar2(4000);\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get download URL\r\n" + 
						"\r\n" + 
						"  Remarks:   see http://s3.amazonaws.com/doc/s3-developer-guide/RESTAuthentication.html   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     15.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  l_epoch := amazon_aws_auth_pkg.get_epoch (p_expiry_date);\r\n" + 
						"  l_signature := amazon_aws_auth_pkg.get_signature ('GET' || chr(10) || chr(10) || chr(10) || l_epoch || chr(10) || '/' || p_bucket_name || '/' || l_key);\r\n" + 
						"  \r\n" + 
						"  l_returnvalue := get_url (p_bucket_name, l_key)\r\n" + 
						"    || '?AWSAccessKeyId=' || amazon_aws_auth_pkg.get_aws_id\r\n" + 
						"    || '&Expires=' || l_epoch\r\n" + 
						"    || '&Signature=' || wwv_flow_utilities.url_encode2 (l_signature);\r\n" + 
						"\r\n" + 
						"  return l_returnvalue;\r\n" + 
						"\r\n" + 
						"end get_download_url;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"procedure new_object (p_bucket_name in varchar2,\r\n" + 
						"                      p_key in varchar2,\r\n" + 
						"                      p_object in blob,\r\n" + 
						"                      p_content_type in varchar2,\r\n" + 
						"                      p_acl in varchar2 := null)\r\n" + 
						"as\r\n" + 
						"\r\n" + 
						"  l_key                          varchar2(4000) := utl_url.escape (p_key);\r\n" + 
						"\r\n" + 
						"  l_clob                         clob;\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"\r\n" + 
						"  l_date_str                     varchar2(255);\r\n" + 
						"  l_auth_str                     varchar2(255);\r\n" + 
						"  \r\n" + 
						"  l_header_names                 t_str_array := t_str_array();\r\n" + 
						"  l_header_values                t_str_array := t_str_array();\r\n" + 
						"\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   upload new object\r\n" + 
						"\r\n" + 
						"  Remarks:   see  http://docs.amazonwebservices.com/AmazonS3/latest/API/RESTObjectPUT.html\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     16.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  l_date_str := amazon_aws_auth_pkg.get_date_string;\r\n" + 
						"  \r\n" + 
						"  if p_acl is not null then\r\n" + 
						"    l_auth_str := amazon_aws_auth_pkg.get_auth_string ('PUT' || chr(10) || chr(10) || p_content_type || chr(10) || l_date_str || chr(10) || 'x-amz-acl:' || p_acl || chr(10) || '/' || p_bucket_name || '/' || l_key);\r\n" + 
						"  else\r\n" + 
						"    l_auth_str := amazon_aws_auth_pkg.get_auth_string ('PUT' || chr(10) || chr(10) || p_content_type || chr(10) || l_date_str || chr(10) || '/' || p_bucket_name || '/' || l_key);\r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(1) := 'Host';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(1) := get_host(p_bucket_name);\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(2) := 'Date';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(2) := l_date_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(3) := 'Authorization';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(3) := l_auth_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(4) := 'Content-Type';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(4) := nvl(p_content_type, 'application/octet-stream');\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(5) := 'Content-Length';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(5) := dbms_lob.getlength(p_object);\r\n" + 
						"  \r\n" + 
						"  if p_acl is not null then\r\n" + 
						"    l_header_names.extend;\r\n" + 
						"    l_header_names(6) := 'x-amz-acl';\r\n" + 
						"    l_header_values.extend;\r\n" + 
						"    l_header_values(6) := p_acl;\r\n" + 
						"  end if;\r\n" + 
						"  \r\n" + 
						"  l_clob := make_request (get_url (p_bucket_name, l_key), 'PUT', l_header_names, l_header_values, p_object);\r\n" + 
						"\r\n" + 
						"  check_for_errors (l_clob);\r\n" + 
						"\r\n" + 
						"end new_object;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"procedure delete_object (p_bucket_name in varchar2,\r\n" + 
						"                         p_key in varchar2)\r\n" + 
						"as\r\n" + 
						"\r\n" + 
						"  l_key                          varchar2(4000) := utl_url.escape (p_key);\r\n" + 
						"\r\n" + 
						"  l_clob                         clob;\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"\r\n" + 
						"  l_date_str                     varchar2(255);\r\n" + 
						"  l_auth_str                     varchar2(255);\r\n" + 
						"  \r\n" + 
						"  l_header_names                 t_str_array := t_str_array();\r\n" + 
						"  l_header_values                t_str_array := t_str_array();\r\n" + 
						"\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   delete object\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     18.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  l_date_str := amazon_aws_auth_pkg.get_date_string;\r\n" + 
						"  l_auth_str := amazon_aws_auth_pkg.get_auth_string ('DELETE' || chr(10) || chr(10) || chr(10) || l_date_str || chr(10) || '/' || p_bucket_name || '/' || l_key);\r\n" + 
						"  \r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(1) := 'Host';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(1) := get_host(p_bucket_name);\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(2) := 'Date';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(2) := l_date_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(3) := 'Authorization';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(3) := l_auth_str;\r\n" + 
						"  \r\n" + 
						"  l_clob := make_request (get_url(p_bucket_name, l_key), 'DELETE', l_header_names, l_header_values);\r\n" + 
						"  \r\n" + 
						"  check_for_errors (l_clob);\r\n" + 
						"\r\n" + 
						"end delete_object;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_object (p_bucket_name in varchar2,\r\n" + 
						"                     p_key in varchar2) return blob\r\n" + 
						"as\r\n" + 
						"  l_returnvalue blob;\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get object\r\n" + 
						"\r\n" + 
						"  Remarks:   \r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  MBR     20.01.2011  Created\r\n" + 
						"  \r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  l_returnvalue := http_util_pkg.get_blob_from_url (get_download_url (p_bucket_name, p_key, sysdate + 1));\r\n" + 
						"\r\n" + 
						"  return l_returnvalue;\r\n" + 
						"\r\n" + 
						"end get_object;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"procedure delete_bucket (p_bucket_name in varchar2)\r\n" + 
						"as\r\n" + 
						"  l_clob                         clob;\r\n" + 
						"  l_date_str                     varchar2(255);\r\n" + 
						"  l_auth_str                     varchar2(255);\r\n" + 
						"  l_header_names                 t_str_array := t_str_array();\r\n" + 
						"  l_header_values                t_str_array := t_str_array();\r\n" + 
						"  l_endpoint                     varchar2(255);\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   delete bucket\r\n" + 
						"\r\n" + 
						"  Remarks:\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  JKEMP   09.08.2012  Created\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  l_date_str := amazon_aws_auth_pkg.get_date_string;\r\n" + 
						"  l_auth_str := amazon_aws_auth_pkg.get_auth_string ('DELETE' || chr(10) || chr(10) || chr(10) || l_date_str || chr(10) || '/' || p_bucket_name || '/');\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(1) := 'Host';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(1) := get_host(p_bucket_name);\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(2) := 'Date';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(2) := l_date_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(3) := 'Authorization';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(3) := l_auth_str;\r\n" + 
						"\r\n" + 
						"  l_clob := make_request (get_url(p_bucket_name), 'DELETE', l_header_names, l_header_values);\r\n" + 
						"\r\n" + 
						"  l_endpoint := check_for_redirect (l_clob);\r\n" + 
						"  \r\n" + 
						"  if l_endpoint is not null then\r\n" + 
						"    l_clob := make_request ('http://' || l_endpoint || '/', 'DELETE', l_header_names, l_header_values);\r\n" + 
						"  end if;\r\n" + 
						"  \r\n" + 
						"  check_for_errors (l_clob);\r\n" + 
						"\r\n" + 
						"end delete_bucket;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_object_acl (p_bucket_name in varchar2,\r\n" + 
						"                         p_key in varchar2) return xmltype\r\n" + 
						"as\r\n" + 
						"                         \r\n" + 
						"  l_clob                         clob;\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"\r\n" + 
						"  l_date_str                     varchar2(255);\r\n" + 
						"  l_auth_str                     varchar2(255);\r\n" + 
						"\r\n" + 
						"  l_header_names                 t_str_array := t_str_array();\r\n" + 
						"  l_header_values                t_str_array := t_str_array();\r\n" + 
						"  \r\n" + 
						"  l_returnvalue                  xmltype;\r\n" + 
						"  \r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get object ACL\r\n" + 
						"  \r\n" + 
						"  Remarks:  get the ACL for an object (private - used by get_object_owner, get_object_grantee_list, get_object_grantee_tab)\r\n" + 
						"\r\n" + 
						"  Example return value:\r\n" + 
						"  \r\n" + 
						"  <AccessControlPolicy xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">\r\n" + 
						"    <Owner>\r\n" + 
						"      <ID>c244a7539c1fc912a06691246c90cb93629690ee4703efac8f08e6ff4cb48ef1</ID>\r\n" + 
						"      <DisplayName>jeffreykemp</DisplayName>\r\n" + 
						"    </Owner>\r\n" + 
						"    <AccessControlList>\r\n" + 
						"      <Grant>\r\n" + 
						"        <Grantee xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CanonicalUser\">\r\n" + 
						"          <ID>c244a7539c1fc912a06691246c90cb93629690ee4703efac8f08e6ff4cb48ef1</ID>\r\n" + 
						"          <DisplayName>jeffreykemp</DisplayName>\r\n" + 
						"        </Grantee>\r\n" + 
						"        <Permission>FULL_CONTROL</Permission>\r\n" + 
						"      </Grant>\r\n" + 
						"      <Grant>\r\n" + 
						"        <Grantee xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Group\">\r\n" + 
						"          <URI>http://acs.amazonaws.com/groups/global/AllUsers</URI>\r\n" + 
						"        </Grantee>\r\n" + 
						"        <Permission>READ</Permission>\r\n" + 
						"      </Grant>\r\n" + 
						"    </AccessControlList>\r\n" + 
						"  </AccessControlPolicy>\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  JKEMP   10.08.2012  Created\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  l_date_str := amazon_aws_auth_pkg.get_date_string;\r\n" + 
						"  l_auth_str := amazon_aws_auth_pkg.get_auth_string ('GET' || chr(10) || chr(10) || chr(10) || l_date_str || chr(10) || '/' || p_bucket_name || '/' || p_key || '?acl');\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(1) := 'Host';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(1) := g_aws_host_s3;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(2) := 'Date';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(2) := l_date_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(3) := 'Authorization';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(3) := l_auth_str;\r\n" + 
						"\r\n" + 
						"  l_clob := make_request (get_url(p_bucket_name, p_key) || '?acl', 'GET', l_header_names, l_header_values, null);\r\n" + 
						"\r\n" + 
						"  if (l_clob is not null) and (length(l_clob) > 0) then\r\n" + 
						"\r\n" + 
						"    l_xml := xmltype (l_clob);\r\n" + 
						"    check_for_errors (l_xml);\r\n" + 
						"    l_returnvalue := l_xml;\r\n" + 
						"\r\n" + 
						"  end if;\r\n" + 
						"\r\n" + 
						"  return l_returnvalue;\r\n" + 
						"\r\n" + 
						"end get_object_acl;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_object_owner (p_bucket_name in varchar2,\r\n" + 
						"                           p_key in varchar2) return t_owner\r\n" + 
						"as\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"  l_returnvalue                  t_owner;\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get owner for an object\r\n" + 
						"\r\n" + 
						"  Remarks:\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  JKEMP   14.08.2012  Created\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  l_xml := get_object_acl (p_bucket_name, p_key);\r\n" + 
						"  \r\n" + 
						"  l_returnvalue.user_id := l_xml.extract('//AccessControlPolicy/Owner/ID/text()', g_aws_namespace_s3_full).getStringVal;\r\n" + 
						"  l_returnvalue.user_name := l_xml.extract('//AccessControlPolicy/Owner/DisplayName/text()', g_aws_namespace_s3_full).getStringVal;\r\n" + 
						"  \r\n" + 
						"  return l_returnvalue;\r\n" + 
						"  \r\n" + 
						"end get_object_owner;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_object_grantee_list (p_bucket_name in varchar2,\r\n" + 
						"                                  p_key in varchar2) return t_grantee_list\r\n" + 
						"as\r\n" + 
						"  l_xml_namespace_s3_full        constant varchar2(255) := 'xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"';\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"  l_count                        pls_integer := 0;\r\n" + 
						"  l_returnvalue                  t_grantee_list;\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get grantees for an object\r\n" + 
						"\r\n" + 
						"  Remarks:   Each grantee will either be a Canonical User or a Group.\r\n" + 
						"             A Canonical User has an ID and a DisplayName.\r\n" + 
						"             A Group has a URI.\r\n" + 
						"             Permission will be FULL_CONTROL, WRITE, or READ_ACP.\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  JKEMP   14.08.2012  Created\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  l_xml := get_object_acl (p_bucket_name, p_key);\r\n" + 
						"\r\n" + 
						"  for l_rec in (\r\n" + 
						"    select extractValue(value(t), '*/Grantee/@xsi:type', g_aws_namespace_s3_full || ' ' || l_xml_namespace_s3_full) as grantee_type,\r\n" + 
						"      extractValue(value(t), '*/Grantee/ID', g_aws_namespace_s3_full) as user_id,\r\n" + 
						"      extractValue(value(t), '*/Grantee/DisplayName', g_aws_namespace_s3_full) as user_name,\r\n" + 
						"      extractValue(value(t), '*/Grantee/URI', g_aws_namespace_s3_full) as group_uri,\r\n" + 
						"      extractValue(value(t), '*/Permission', g_aws_namespace_s3_full) as permission\r\n" + 
						"    from table(xmlsequence(l_xml.extract('//AccessControlPolicy/AccessControlList/Grant', g_aws_namespace_s3_full))) t\r\n" + 
						"    ) loop\r\n" + 
						"    l_count := l_count + 1;\r\n" + 
						"    l_returnvalue(l_count).grantee_type := l_rec.grantee_type;\r\n" + 
						"    l_returnvalue(l_count).user_id := l_rec.user_id;\r\n" + 
						"    l_returnvalue(l_count).user_name := l_rec.user_name;\r\n" + 
						"    l_returnvalue(l_count).group_uri := l_rec.group_uri;\r\n" + 
						"    l_returnvalue(l_count).permission := l_rec.permission;\r\n" + 
						"  end loop;\r\n" + 
						"  \r\n" + 
						"  return l_returnvalue;\r\n" + 
						"\r\n" + 
						"end get_object_grantee_list;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"function get_object_grantee_tab (p_bucket_name in varchar2,\r\n" + 
						"                                 p_key in varchar2) return t_grantee_tab pipelined\r\n" + 
						"as\r\n" + 
						"  l_grantee_list  t_grantee_list;\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   get grantees for an object\r\n" + 
						"\r\n" + 
						"  Remarks:\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  JKEMP   14.08.2012  Created\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"  \r\n" + 
						"  l_grantee_list := get_object_grantee_list (p_bucket_name, p_key);\r\n" + 
						"\r\n" + 
						"  for i in 1 .. l_grantee_list.count loop\r\n" + 
						"    pipe row (l_grantee_list(i));\r\n" + 
						"  end loop;\r\n" + 
						"\r\n" + 
						"  return;\r\n" + 
						"  \r\n" + 
						"end get_object_grantee_tab;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"procedure set_object_acl (p_bucket_name in varchar2,\r\n" + 
						"                          p_key in varchar2,\r\n" + 
						"                          p_acl in varchar2)\r\n" + 
						"as\r\n" + 
						"  l_key                          varchar2(4000) := utl_url.escape (p_key);\r\n" + 
						"  l_clob                         clob;\r\n" + 
						"  l_xml                          xmltype;\r\n" + 
						"  l_date_str                     varchar2(255);\r\n" + 
						"  l_auth_str                     varchar2(255);\r\n" + 
						"  l_header_names                 t_str_array := t_str_array();\r\n" + 
						"  l_header_values                t_str_array := t_str_array();\r\n" + 
						"begin\r\n" + 
						"\r\n" + 
						"  /*\r\n" + 
						"\r\n" + 
						"  Purpose:   modify the access control list (owner and grantees) for an object\r\n" + 
						"\r\n" + 
						"  Remarks:   see http://code.google.com/p/plsql-utils/issues/detail?id=17\r\n" + 
						"\r\n" + 
						"  Who     Date        Description\r\n" + 
						"  ------  ----------  -------------------------------------\r\n" + 
						"  JKEMP   22.09.2012  Created\r\n" + 
						"\r\n" + 
						"  */\r\n" + 
						"\r\n" + 
						"  l_date_str := amazon_aws_auth_pkg.get_date_string;\r\n" + 
						"  l_auth_str := amazon_aws_auth_pkg.get_auth_string ('PUT' || chr(10) || chr(10) || chr(10) || l_date_str || chr(10) || 'x-amz-acl:' || p_acl || chr(10) || '/' || p_bucket_name || '/' || l_key || '?acl');\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(1) := 'Host';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(1) := get_host(p_bucket_name);\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(2) := 'Date';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(2) := l_date_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(3) := 'Authorization';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(3) := l_auth_str;\r\n" + 
						"\r\n" + 
						"  l_header_names.extend;\r\n" + 
						"  l_header_names(4) := 'x-amz-acl';\r\n" + 
						"  l_header_values.extend;\r\n" + 
						"  l_header_values(4) := p_acl;\r\n" + 
						"\r\n" + 
						"  l_clob := make_request (get_url(p_bucket_name, l_key) || '?acl', 'PUT', l_header_names, l_header_values);\r\n" + 
						"\r\n" + 
						"  check_for_errors (l_clob);\r\n" + 
						"\r\n" + 
						"end set_object_acl;\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"end amazon_aws_s3_pkg;\r\n" + 
						"/\r\n" + 
						"\r\n" + 
						"")
			   ,new TestSourceRec(2, "amazon_aws_s3_pkg", "PACKAGE", "create or replace package amazon_aws_s3_pkg\r\n" + 
			   		"as\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   PL/SQL wrapper package for Amazon AWS S3 API\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:   inspired by the whitepaper \"Building an Amazon S3 Client with Application Express 4.0\" by Jason Straub\r\n" + 
			   		"             see http://jastraub.blogspot.com/2011/01/building-amazon-s3-client-with.html\r\n" + 
			   		"             \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     09.01.2011  Created\r\n" + 
			   		"  MBR     16.02.2013  Added enhancements from Jeffrey Kemp, see http://code.google.com/p/plsql-utils/issues/detail?id=14 to http://code.google.com/p/plsql-utils/issues/detail?id=17\r\n" + 
			   		"  \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  type t_bucket is record (\r\n" + 
			   		"    bucket_name varchar2(255),\r\n" + 
			   		"    creation_date date\r\n" + 
			   		"  );\r\n" + 
			   		"\r\n" + 
			   		"  type t_bucket_list is table of t_bucket index by binary_integer;\r\n" + 
			   		"  type t_bucket_tab is table of t_bucket;\r\n" + 
			   		"  \r\n" + 
			   		"  type t_object is record (\r\n" + 
			   		"    key varchar2(4000),\r\n" + 
			   		"    size_bytes number,\r\n" + 
			   		"    last_modified date\r\n" + 
			   		"  );\r\n" + 
			   		"\r\n" + 
			   		"  type t_object_list is table of t_object index by binary_integer;\r\n" + 
			   		"  type t_object_tab is table of t_object;\r\n" + 
			   		"  \r\n" + 
			   		"  type t_owner is record (\r\n" + 
			   		"    user_id varchar2(200),\r\n" + 
			   		"    user_name varchar2(200)\r\n" + 
			   		"  );\r\n" + 
			   		"\r\n" + 
			   		"  type t_grantee is record (\r\n" + 
			   		"    grantee_type varchar2(20),  -- CanonicalUser or Group\r\n" + 
			   		"    user_id varchar2(200),      -- for users\r\n" + 
			   		"    user_name varchar2(200),    -- for users\r\n" + 
			   		"    group_uri varchar2(200),    -- for groups\r\n" + 
			   		"    permission varchar2(20)     -- FULL_CONTROL, WRITE, READ_ACP\r\n" + 
			   		"  );\r\n" + 
			   		"  \r\n" + 
			   		"  type t_grantee_list is table of t_grantee index by binary_integer;\r\n" + 
			   		"  type t_grantee_tab is table of t_grantee;\r\n" + 
			   		"  \r\n" + 
			   		"  -- bucket regions \r\n" + 
			   		"  -- see http://aws.amazon.com/articles/3912?_encoding=UTF8&jiveRedirect=1#s3\r\n" + 
			   		"  -- see http://docs.aws.amazon.com/general/latest/gr/rande.html#s3_region\r\n" + 
			   		"  g_region_us_standard           constant varchar2(255) := null;\r\n" + 
			   		"  g_region_us_west_california    constant varchar2(255) := 'us-west-1';\r\n" + 
			   		"  g_region_us_west_oregon        constant varchar2(255) := 'us-west-2';\r\n" + 
			   		"  g_region_eu_ireland            constant varchar2(255) := 'EU';\r\n" + 
			   		"  g_region_asia_pacific_singapor constant varchar2(255) := 'ap-southeast-1';\r\n" + 
			   		"  g_region_asia_pacific_sydney   constant varchar2(255) := 'ap-southeast-2';\r\n" + 
			   		"  g_region_asia_pacific_tokyo    constant varchar2(255) := 'ap-northeast-1';\r\n" + 
			   		"  g_region_south_america_sao_p   constant varchar2(255) := 'sa-east-1';\r\n" + 
			   		"  \r\n" + 
			   		"  -- deprecated region constants, will be removed in next release (use constants above instead)\r\n" + 
			   		"  g_region_eu                    constant varchar2(255) := 'EU';\r\n" + 
			   		"  g_region_us_west_1             constant varchar2(255) := 'us-west-1';\r\n" + 
			   		"  g_region_us_west_2             constant varchar2(255) := 'us-west-2';\r\n" + 
			   		"  g_region_asia_pacific_1        constant varchar2(255) := 'ap-southeast-1'; \r\n" + 
			   		"  \r\n" + 
			   		"  -- predefined access policies\r\n" + 
			   		"  -- see http://docs.amazonwebservices.com/AmazonS3/latest/dev/index.html?RESTAccessPolicy.html\r\n" + 
			   		"\r\n" + 
			   		"  g_acl_private                  constant varchar2(255) := 'private';\r\n" + 
			   		"  g_acl_public_read              constant varchar2(255) := 'public-read';\r\n" + 
			   		"  g_acl_public_read_write        constant varchar2(255) := 'public-read-write';\r\n" + 
			   		"  g_acl_authenticated_read       constant varchar2(255) := 'authenticated-read';\r\n" + 
			   		"  g_acl_bucket_owner_read        constant varchar2(255) := 'bucket-owner-read';\r\n" + 
			   		"  g_acl_bucket_owner_full_ctrl   constant varchar2(255) := 'bucket-owner-full-control';\r\n" + 
			   		"\r\n" + 
			   		"  -- get buckets\r\n" + 
			   		"  function get_bucket_list return t_bucket_list;\r\n" + 
			   		"\r\n" + 
			   		"  -- get buckets\r\n" + 
			   		"  function get_bucket_tab return t_bucket_tab pipelined;\r\n" + 
			   		"  \r\n" + 
			   		"  -- create bucket\r\n" + 
			   		"  procedure new_bucket (p_bucket_name in varchar2,\r\n" + 
			   		"                        p_region in varchar2 := null);\r\n" + 
			   		"\r\n" + 
			   		"  -- get bucket region\r\n" + 
			   		"  function get_bucket_region (p_bucket_name in varchar2) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- get objects\r\n" + 
			   		"  function get_object_list (p_bucket_name in varchar2,\r\n" + 
			   		"                            p_prefix in varchar2 := null,\r\n" + 
			   		"                            p_max_keys in number := null) return t_object_list;\r\n" + 
			   		"\r\n" + 
			   		"  -- get objects\r\n" + 
			   		"  function get_object_tab (p_bucket_name in varchar2,\r\n" + 
			   		"                           p_prefix in varchar2 := null,\r\n" + 
			   		"                           p_max_keys in number := null) return t_object_tab pipelined;\r\n" + 
			   		"\r\n" + 
			   		"  -- get download URL\r\n" + 
			   		"  function get_download_url (p_bucket_name in varchar2,\r\n" + 
			   		"                             p_key in varchar2,\r\n" + 
			   		"                             p_expiry_date in date) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- new object\r\n" + 
			   		"  procedure new_object (p_bucket_name in varchar2,\r\n" + 
			   		"                        p_key in varchar2,\r\n" + 
			   		"                        p_object in blob,\r\n" + 
			   		"                        p_content_type in varchar2,\r\n" + 
			   		"                        p_acl in varchar2 := null);\r\n" + 
			   		"                        \r\n" + 
			   		"  -- delete object\r\n" + 
			   		"  procedure delete_object (p_bucket_name in varchar2,\r\n" + 
			   		"                           p_key in varchar2);\r\n" + 
			   		"\r\n" + 
			   		"  -- get object\r\n" + 
			   		"  function get_object (p_bucket_name in varchar2,\r\n" + 
			   		"                       p_key in varchar2) return blob;\r\n" + 
			   		"\r\n" + 
			   		"  -- delete bucket\r\n" + 
			   		"  procedure delete_bucket (p_bucket_name in varchar2);\r\n" + 
			   		"\r\n" + 
			   		"  -- get owner for an object\r\n" + 
			   		"  function get_object_owner (p_bucket_name in varchar2,\r\n" + 
			   		"                             p_key in varchar2) return t_owner;\r\n" + 
			   		"\r\n" + 
			   		"  -- get grantees for an object\r\n" + 
			   		"  function get_object_grantee_list (p_bucket_name in varchar2,\r\n" + 
			   		"                                    p_key in varchar2) return t_grantee_list;\r\n" + 
			   		"\r\n" + 
			   		"  -- get grantees for an object\r\n" + 
			   		"  function get_object_grantee_tab (p_bucket_name in varchar2,\r\n" + 
			   		"                                   p_key in varchar2) return t_grantee_tab pipelined;\r\n" + 
			   		"\r\n" + 
			   		"  -- modify the access control list for an object\r\n" + 
			   		"  procedure set_object_acl (p_bucket_name in varchar2,\r\n" + 
			   		"                            p_key in varchar2,\r\n" + 
			   		"                            p_acl in varchar2);\r\n" + 
			   		"\r\n" + 
			   		"end amazon_aws_s3_pkg;\r\n" + 
			   		"/\r\n" + 
			   		"\r\n" + 
			   		"")
			   ,new TestSourceRec(3, "amazon_aws_auth_pkg", "PACKAGE", "create or replace package amazon_aws_auth_pkg\r\n" + 
			   		"as\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   PL/SQL wrapper package for Amazon AWS authentication API\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:   inspired by the whitepaper \"Building an Amazon S3 Client with Application Express 4.0\" by Jason Straub\r\n" + 
			   		"             see http://jastraub.blogspot.com/2011/01/building-amazon-s3-client-with.html\r\n" + 
			   		"\r\n" + 
			   		"             dependencies: owner of this package needs execute on dbms_crypto\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     09.01.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  -- get \"Authorization\" (actually authentication) header string\r\n" + 
			   		"  function get_auth_string (p_string in varchar2) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- get signature string\r\n" + 
			   		"  function get_signature (p_string in varchar2) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- get AWS access key ID\r\n" + 
			   		"  function get_aws_id return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- get date string\r\n" + 
			   		"  function get_date_string (p_date in date := sysdate) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- get epoch (number of seconds since January 1, 1970)\r\n" + 
			   		"  function get_epoch (p_date in date) return number;\r\n" + 
			   		"\r\n" + 
			   		"  -- set AWS access key id\r\n" + 
			   		"  procedure set_aws_id (p_aws_id in varchar2);\r\n" + 
			   		"\r\n" + 
			   		"  -- set AWS secret key\r\n" + 
			   		"  procedure set_aws_key (p_aws_key in varchar2);\r\n" + 
			   		"\r\n" + 
			   		"  -- set GMT offset\r\n" + 
			   		"  procedure set_gmt_offset (p_gmt_offset in number);\r\n" + 
			   		"\r\n" + 
			   		"  -- initialize package for use\r\n" + 
			   		"  procedure init (p_aws_id in varchar2,\r\n" + 
			   		"                  p_aws_key in varchar2,\r\n" + 
			   		"                  p_gmt_offset in number := NULL);\r\n" + 
			   		"\r\n" + 
			   		"end amazon_aws_auth_pkg;\r\n" + 
			   		"/\r\n" + 
			   		"")
			   ,new TestSourceRec(4, "amazon_aws_auth_pkg", "PACKAGE BODY", "create or replace package body amazon_aws_auth_pkg\r\n" + 
			   		"as\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   PL/SQL wrapper package for Amazon AWS authentication API\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:   inspired by the whitepaper \"Building an Amazon S3 Client with Application Express 4.0\" by Jason Straub\r\n" + 
			   		"             see http://jastraub.blogspot.com/2011/01/building-amazon-s3-client-with.html\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     09.01.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  g_aws_id                 varchar2(20) := 'my_aws_id'; -- AWS access key ID\r\n" + 
			   		"  g_aws_key                varchar2(40) := 'my_aws_key'; -- AWS secret key\r\n" + 
			   		"\r\n" + 
			   		"  g_gmt_offset             number := NULL; -- your timezone GMT adjustment\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_auth_string (p_string in varchar2) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		" l_returnvalue      varchar2(32000);\r\n" + 
			   		" l_encrypted_raw    raw (2000);             -- stores encrypted binary text\r\n" + 
			   		" l_decrypted_raw    raw (2000);             -- stores decrypted binary text\r\n" + 
			   		" l_key_bytes_raw    raw (64);               -- stores 256-bit encryption key\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   get authentication string\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:   see http://docs.amazonwebservices.com/AmazonS3/latest/dev/RESTAuthentication.html#ConstructingTheAuthenticationHeader\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     09.01.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  l_key_bytes_raw := utl_i18n.string_to_raw (g_aws_key,  'AL32UTF8');\r\n" + 
			   		"  l_decrypted_raw := utl_i18n.string_to_raw (p_string, 'AL32UTF8');\r\n" + 
			   		"\r\n" + 
			   		"  l_encrypted_raw := dbms_crypto.mac (src => l_decrypted_raw, typ => dbms_crypto.hmac_sh1, key => l_key_bytes_raw);\r\n" + 
			   		"\r\n" + 
			   		"  l_returnvalue := utl_i18n.raw_to_char (utl_encode.base64_encode(l_encrypted_raw), 'AL32UTF8');\r\n" + 
			   		"\r\n" + 
			   		"  l_returnvalue := 'AWS ' || g_aws_id || ':' || l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"end get_auth_string;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_signature (p_string in varchar2) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   get signature part of authentication string\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     09.01.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  return substr(get_auth_string(p_string),26);\r\n" + 
			   		"\r\n" + 
			   		"end get_signature;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_aws_id return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   get AWS access key ID\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     09.01.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  return g_aws_id;\r\n" + 
			   		"\r\n" + 
			   		"end get_aws_id;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_date_string (p_date in date := sysdate) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue varchar2(255);\r\n" + 
			   		"  l_date_as_time timestamp(6);\r\n" + 
			   		"  l_time_utc timestamp(6);\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   get AWS access key ID\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     09.01.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  if g_gmt_offset is null then\r\n" + 
			   		"    l_date_as_time := cast(p_date as timestamp);\r\n" + 
			   		"    l_time_utc := sys_extract_utc(l_date_as_time);\r\n" + 
			   		"    l_returnvalue := to_char(l_time_utc, 'Dy, DD Mon YYYY HH24:MI:SS', 'NLS_DATE_LANGUAGE = AMERICAN') || ' GMT';\r\n" + 
			   		"  else\r\n" + 
			   		"    l_returnvalue := to_char(p_date + g_gmt_offset/24, 'Dy, DD Mon YYYY HH24:MI:SS', 'NLS_DATE_LANGUAGE = AMERICAN') || ' GMT';\r\n" + 
			   		"  end if;\r\n" + 
			   		"\r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"end get_date_string;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_epoch (p_date in date) return number\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue number;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   get epoch (number of seconds since January 1, 1970)\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     09.01.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  l_returnvalue := trunc((p_date - to_date('01-01-1970','MM-DD-YYYY')) * 24 * 60 * 60);\r\n" + 
			   		"\r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"end get_epoch;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"procedure set_aws_id (p_aws_id in varchar2)\r\n" + 
			   		"as\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   set AWS access key id\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     18.01.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  g_aws_id := p_aws_id;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"end set_aws_id;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"procedure set_aws_key (p_aws_key in varchar2)\r\n" + 
			   		"as\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   set AWS secret key\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     18.01.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  g_aws_key := p_aws_key;\r\n" + 
			   		"\r\n" + 
			   		"end set_aws_key;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"procedure set_gmt_offset (p_gmt_offset in number)\r\n" + 
			   		"as\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   set GMT offset\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     03.03.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  g_gmt_offset := p_gmt_offset;\r\n" + 
			   		"\r\n" + 
			   		"end set_gmt_offset;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"procedure init (p_aws_id in varchar2,\r\n" + 
			   		"                p_aws_key in varchar2,\r\n" + 
			   		"                p_gmt_offset in number := NULL)\r\n" + 
			   		"as\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:   initialize package for use\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     03.03.2011  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  g_aws_id := p_aws_id;\r\n" + 
			   		"  g_aws_key := p_aws_key;\r\n" + 
			   		"  g_gmt_offset := p_gmt_offset;\r\n" + 
			   		"\r\n" + 
			   		"end init;\r\n" + 
			   		"\r\n" + 
			   		"end amazon_aws_auth_pkg;\r\n" + 
			   		"/\r\n" + 
			   		"")
			   ,new TestSourceRec(5, "csv_util_pkg", "PACKAGE", "create or replace package csv_util_pkg\r\n" + 
			   		"as\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      Package handles comma-separated values (CSV)\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     31.03.2010  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  g_default_separator            constant varchar2(1) := ',';\r\n" + 
			   		" \r\n" + 
			   		" \r\n" + 
			   		"  -- convert CSV line to array of values\r\n" + 
			   		"  function csv_to_array (p_csv_line in varchar2,\r\n" + 
			   		"                         p_separator in varchar2 := g_default_separator) return t_str_array;\r\n" + 
			   		" \r\n" + 
			   		"  -- convert array of values to CSV\r\n" + 
			   		"  function array_to_csv (p_values in t_str_array,\r\n" + 
			   		"                         p_separator in varchar2 := g_default_separator) return varchar2;\r\n" + 
			   		" \r\n" + 
			   		"  -- get value from array by position\r\n" + 
			   		"  function get_array_value (p_values in t_str_array,\r\n" + 
			   		"                            p_position in number,\r\n" + 
			   		"                            p_column_name in varchar2 := null) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- convert clob to CSV\r\n" + 
			   		"  function clob_to_csv (p_csv_clob in clob,\r\n" + 
			   		"                        p_separator in varchar2 := g_default_separator,\r\n" + 
			   		"                        p_skip_rows in number := 0) return t_csv_tab pipelined;\r\n" + 
			   		"\r\n" + 
			   		"end csv_util_pkg;\r\n" + 
			   		"/\r\n" + 
			   		"\r\n" + 
			   		"")
			   ,new TestSourceRec(6, "csv_util_pkg", "PACKAGE BODY", "create or replace package body csv_util_pkg\r\n" + 
			   		"as\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      Package handles comma-separated values (CSV)\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     31.03.2010  Created\r\n" + 
			   		"  KJS     20.04.2011  Modified to allow double-quote escaping\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		" \r\n" + 
			   		"function csv_to_array (p_csv_line in varchar2,\r\n" + 
			   		"                       p_separator in varchar2 := g_default_separator) return t_str_array\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue      t_str_array     := t_str_array();\r\n" + 
			   		"  l_length           pls_integer     := length(p_csv_line);\r\n" + 
			   		"  l_idx              binary_integer  := 1;\r\n" + 
			   		"  l_quoted           boolean         := false;  \r\n" + 
			   		"  l_quote  constant  varchar2(1)     := '\"';\r\n" + 
			   		"  l_start            boolean := true;\r\n" + 
			   		"  l_current          varchar2(1 char);\r\n" + 
			   		"  l_next             varchar2(1 char);\r\n" + 
			   		"  l_position         pls_integer := 1;\r\n" + 
			   		"  l_current_column   varchar2(32767);\r\n" + 
			   		"  \r\n" + 
			   		"  --Set the start flag, save our column value\r\n" + 
			   		"  procedure save_column is\r\n" + 
			   		"  begin\r\n" + 
			   		"    l_start := true;\r\n" + 
			   		"    l_returnvalue.extend;        \r\n" + 
			   		"    l_returnvalue(l_idx) := l_current_column;\r\n" + 
			   		"    l_idx := l_idx + 1;            \r\n" + 
			   		"    l_current_column := null;\r\n" + 
			   		"  end save_column;\r\n" + 
			   		"  \r\n" + 
			   		"  --Append the value of l_current to l_current_column\r\n" + 
			   		"  procedure append_current is\r\n" + 
			   		"  begin\r\n" + 
			   		"    l_current_column := l_current_column || l_current;\r\n" + 
			   		"  end append_current;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      convert CSV line to array of values\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      based on code from http://www.experts-exchange.com/Database/Oracle/PL_SQL/Q_23106446.html\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     31.03.2010  Created\r\n" + 
			   		"  KJS     20.04.2011  Modified to allow double-quote escaping\r\n" + 
			   		"  MBR     23.07.2012  Fixed issue with multibyte characters, thanks to Vadi..., see http://code.google.com/p/plsql-utils/issues/detail?id=13\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  while l_position <= l_length loop\r\n" + 
			   		"  \r\n" + 
			   		"    --Set our variables with the current and next characters\r\n" + 
			   		"    l_current := substr(p_csv_line, l_position, 1);\r\n" + 
			   		"    l_next := substr(p_csv_line, l_position + 1, 1);    \r\n" + 
			   		"    \r\n" + 
			   		"    if l_start then\r\n" + 
			   		"      l_start := false;\r\n" + 
			   		"      l_current_column := null;\r\n" + 
			   		"    \r\n" + 
			   		"      --Check for leading quote and set our flag\r\n" + 
			   		"      l_quoted := l_current = l_quote;\r\n" + 
			   		"      \r\n" + 
			   		"      --We skip a leading quote character\r\n" + 
			   		"      if l_quoted then goto loop_again; end if;\r\n" + 
			   		"    end if;\r\n" + 
			   		"\r\n" + 
			   		"    --Check to see if we are inside of a quote    \r\n" + 
			   		"    if l_quoted then      \r\n" + 
			   		"\r\n" + 
			   		"      --The current character is a quote - is it the end of our quote or does\r\n" + 
			   		"      --it represent an escaped quote?\r\n" + 
			   		"      if l_current = l_quote then\r\n" + 
			   		"\r\n" + 
			   		"        --If the next character is a quote, this is an escaped quote.\r\n" + 
			   		"        if l_next = l_quote then\r\n" + 
			   		"        \r\n" + 
			   		"          --Append the literal quote to our column\r\n" + 
			   		"          append_current;\r\n" + 
			   		"          \r\n" + 
			   		"          --Advance the pointer to ignore the duplicated (escaped) quote\r\n" + 
			   		"          l_position := l_position + 1;\r\n" + 
			   		"          \r\n" + 
			   		"        --If the next character is a separator, current is the end quote\r\n" + 
			   		"        elsif l_next = p_separator then\r\n" + 
			   		"          \r\n" + 
			   		"          --Get out of the quote and loop again - we will hit the separator next loop\r\n" + 
			   		"          l_quoted := false;\r\n" + 
			   		"          goto loop_again;\r\n" + 
			   		"        \r\n" + 
			   		"        --Ending quote, no more columns\r\n" + 
			   		"        elsif l_next is null then\r\n" + 
			   		"\r\n" + 
			   		"          --Save our current value, and iterate (end loop)\r\n" + 
			   		"          save_column;\r\n" + 
			   		"          goto loop_again;          \r\n" + 
			   		"          \r\n" + 
			   		"        --Next character is not a quote\r\n" + 
			   		"        else\r\n" + 
			   		"          append_current;\r\n" + 
			   		"        end if;\r\n" + 
			   		"      else\r\n" + 
			   		"      \r\n" + 
			   		"        --The current character is not a quote - append it to our column value\r\n" + 
			   		"        append_current;     \r\n" + 
			   		"      end if;\r\n" + 
			   		"      \r\n" + 
			   		"    -- Not quoted\r\n" + 
			   		"    else\r\n" + 
			   		"    \r\n" + 
			   		"      --Check if the current value is a separator, save or append as appropriate\r\n" + 
			   		"      if l_current = p_separator then\r\n" + 
			   		"        save_column;\r\n" + 
			   		"      else\r\n" + 
			   		"        append_current;\r\n" + 
			   		"      end if;\r\n" + 
			   		"    end if;\r\n" + 
			   		"    \r\n" + 
			   		"    --Check to see if we've used all our characters\r\n" + 
			   		"    if l_next is null then\r\n" + 
			   		"      save_column;\r\n" + 
			   		"    end if;\r\n" + 
			   		"\r\n" + 
			   		"    --The continue statement was not added to PL/SQL until 11g. Use GOTO in 9i.\r\n" + 
			   		"    <<loop_again>> l_position := l_position + 1;\r\n" + 
			   		"  end loop ;\r\n" + 
			   		"  \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"end csv_to_array;\r\n" + 
			   		" \r\n" + 
			   		" \r\n" + 
			   		"function array_to_csv (p_values in t_str_array,\r\n" + 
			   		"                       p_separator in varchar2 := g_default_separator) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_value       varchar2(32767);\r\n" + 
			   		"  l_returnvalue varchar2(32767);\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      convert array of values to CSV\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     31.03.2010  Created\r\n" + 
			   		"  KJS     20.04.2011  Modified to allow quoted data, fixed a bug when 1st col was null\r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  for i in p_values.first .. p_values.last loop\r\n" + 
			   		"  \r\n" + 
			   		"    --Double quotes must be escaped\r\n" + 
			   		"    l_value := replace(p_values(i), '\"', '\"\"');\r\n" + 
			   		"    \r\n" + 
			   		"    --Values containing the separator, a double quote, or a new line must be quoted.\r\n" + 
			   		"    if instr(l_value, p_separator) > 0 or instr(l_value, '\"') > 0 or instr(l_value, chr(10)) > 0 then\r\n" + 
			   		"      l_value := '\"' || l_value || '\"';\r\n" + 
			   		"    end if;\r\n" + 
			   		"    \r\n" + 
			   		"    --Append our value to our return value\r\n" + 
			   		"    if i = p_values.first then\r\n" + 
			   		"      l_returnvalue := l_value;\r\n" + 
			   		"    else\r\n" + 
			   		"      l_returnvalue := l_returnvalue || p_separator || l_value;\r\n" + 
			   		"    end if;\r\n" + 
			   		"  end loop;\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end array_to_csv;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_array_value (p_values in t_str_array,\r\n" + 
			   		"                          p_position in number,\r\n" + 
			   		"                          p_column_name in varchar2 := null) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue varchar2(4000);\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      get value from array by position\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:     \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     31.03.2010  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  if p_values.count >= p_position then\r\n" + 
			   		"    l_returnvalue := p_values(p_position);\r\n" + 
			   		"  else\r\n" + 
			   		"    if p_column_name is not null then\r\n" + 
			   		"      raise_application_error (-20000, 'Column number ' || p_position || ' does not exist. Expected column: ' || p_column_name);\r\n" + 
			   		"    else\r\n" + 
			   		"      l_returnvalue := null;\r\n" + 
			   		"    end if;\r\n" + 
			   		"  end if;\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_array_value;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function clob_to_csv (p_csv_clob in clob,\r\n" + 
			   		"                      p_separator in varchar2 := g_default_separator,\r\n" + 
			   		"                      p_skip_rows in number := 0) return t_csv_tab pipelined\r\n" + 
			   		"as\r\n" + 
			   		"  l_csv_clob               clob;\r\n" + 
			   		"  l_line_separator         varchar2(2) := chr(13) || chr(10);\r\n" + 
			   		"  l_last                   pls_integer;\r\n" + 
			   		"  l_current                pls_integer;\r\n" + 
			   		"  l_line                   varchar2(32000);\r\n" + 
			   		"  l_line_number            pls_integer := 0;\r\n" + 
			   		"  l_from_line              pls_integer := p_skip_rows + 1;\r\n" + 
			   		"  l_line_array             t_str_array;\r\n" + 
			   		"  l_row                    t_csv_line := t_csv_line (null, null,  -- line number, line raw\r\n" + 
			   		"                                                     null, null, null, null, null, null, null, null, null, null,   -- lines 1-10\r\n" + 
			   		"                                                     null, null, null, null, null, null, null, null, null, null);  -- lines 11-20\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      convert clob to CSV\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      based on code from http://asktom.oracle.com/pls/asktom/f?p=100:11:0::::P11_QUESTION_ID:1352202934074\r\n" + 
			   		"                              and  http://asktom.oracle.com/pls/asktom/f?p=100:11:0::::P11_QUESTION_ID:744825627183\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     31.03.2010  Created\r\n" + 
			   		"  JLL     20.04.2015  Modified made an internal clob because || l_line_separator is very bad for performance\r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  -- If the file has a DOS newline (cr+lf), use that\r\n" + 
			   		"  -- If the file does not have a DOS newline, use a Unix newline (lf)\r\n" + 
			   		"  if (nvl(dbms_lob.instr(p_csv_clob, l_line_separator, 1, 1),0) = 0) then\r\n" + 
			   		"    l_line_separator := chr(10);\r\n" + 
			   		"  end if;\r\n" + 
			   		"\r\n" + 
			   		"  l_last := 1;\r\n" + 
			   		"  l_csv_clob := p_csv_clob || l_line_separator;\r\n" + 
			   		"\r\n" + 
			   		"  loop\r\n" + 
			   		"  \r\n" + 
			   		"    l_current := dbms_lob.instr (l_csv_clob , l_line_separator, l_last, 1);\r\n" + 
			   		"    exit when (nvl(l_current,0) = 0);\r\n" + 
			   		"    \r\n" + 
			   		"    l_line_number := l_line_number + 1;\r\n" + 
			   		"    \r\n" + 
			   		"    if l_from_line <= l_line_number then\r\n" + 
			   		"    \r\n" + 
			   		"      l_line := dbms_lob.substr(l_csv_clob, l_current - l_last + 1, l_last);\r\n" + 
			   		"      --l_line := replace(l_line, l_line_separator, '');\r\n" + 
			   		"      l_line := replace(l_line, chr(10), '');\r\n" + 
			   		"      l_line := replace(l_line, chr(13), '');\r\n" + 
			   		"\r\n" + 
			   		"      l_line_array := csv_to_array (l_line, p_separator);\r\n" + 
			   		"\r\n" + 
			   		"      l_row.line_number := l_line_number;\r\n" + 
			   		"      l_row.line_raw := substr(l_line,1,4000);\r\n" + 
			   		"      l_row.c001 := get_array_value (l_line_array, 1);\r\n" + 
			   		"      l_row.c002 := get_array_value (l_line_array, 2);\r\n" + 
			   		"      l_row.c003 := get_array_value (l_line_array, 3);\r\n" + 
			   		"      l_row.c004 := get_array_value (l_line_array, 4);\r\n" + 
			   		"      l_row.c005 := get_array_value (l_line_array, 5);\r\n" + 
			   		"      l_row.c006 := get_array_value (l_line_array, 6);\r\n" + 
			   		"      l_row.c007 := get_array_value (l_line_array, 7);\r\n" + 
			   		"      l_row.c008 := get_array_value (l_line_array, 8);\r\n" + 
			   		"      l_row.c009 := get_array_value (l_line_array, 9);\r\n" + 
			   		"      l_row.c010 := get_array_value (l_line_array, 10);\r\n" + 
			   		"      l_row.c011 := get_array_value (l_line_array, 11);\r\n" + 
			   		"      l_row.c012 := get_array_value (l_line_array, 12);\r\n" + 
			   		"      l_row.c013 := get_array_value (l_line_array, 13);\r\n" + 
			   		"      l_row.c014 := get_array_value (l_line_array, 14);\r\n" + 
			   		"      l_row.c015 := get_array_value (l_line_array, 15);\r\n" + 
			   		"      l_row.c016 := get_array_value (l_line_array, 16);\r\n" + 
			   		"      l_row.c017 := get_array_value (l_line_array, 17);\r\n" + 
			   		"      l_row.c018 := get_array_value (l_line_array, 18);\r\n" + 
			   		"      l_row.c019 := get_array_value (l_line_array, 19);\r\n" + 
			   		"      l_row.c020 := get_array_value (l_line_array, 20);\r\n" + 
			   		"      \r\n" + 
			   		"      pipe row (l_row);\r\n" + 
			   		"      \r\n" + 
			   		"    end if;\r\n" + 
			   		"\r\n" + 
			   		"    l_last := l_current + length (l_line_separator);\r\n" + 
			   		"\r\n" + 
			   		"  end loop;\r\n" + 
			   		"\r\n" + 
			   		"  return;\r\n" + 
			   		" \r\n" + 
			   		"end clob_to_csv;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"end csv_util_pkg;\r\n" + 
			   		"/\r\n" + 
			   		" \r\n" + 
			   		"")
			   ,new TestSourceRec(7, "crypto_util_pkg", "PACKAGE", "create or replace package crypto_util_pkg\r\n" + 
			   		"as\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:    Package handles encryption/decryption\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:    see http://download.oracle.com/docs/cd/B14117_01/network.101/b10773/apdvncrp.htm\r\n" + 
			   		"              see \"Effective Oracle Database 10g Security\" by David Knox (McGraw Hill 2004)\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     20.01.2011  Created\r\n" + 
			   		"  \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"  -- encrypt blob\r\n" + 
			   		"  function encrypt_aes256 (p_blob in blob,\r\n" + 
			   		"                           p_key in varchar2) return blob;\r\n" + 
			   		"\r\n" + 
			   		"  -- decrypt blob\r\n" + 
			   		"  function decrypt_aes256 (p_blob in blob,\r\n" + 
			   		"                           p_key in varchar2) return blob;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"end crypto_util_pkg;\r\n" + 
			   		"/\r\n" + 
			   		"\r\n" + 
			   		"")
			   ,new TestSourceRec(8, "crypto_util_pkg", "PACKAGE BODY", "create or replace package body crypto_util_pkg\r\n" + 
			   		"as\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:    Package handles encryption/decryption\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:  \r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     20.01.2011  Created\r\n" + 
			   		"  \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  g_encryption_type_aes          constant pls_integer := dbms_crypto.encrypt_aes256 + dbms_crypto.chain_cbc + dbms_crypto.pad_pkcs5;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function encrypt_aes256 (p_blob in blob,\r\n" + 
			   		"                         p_key in varchar2) return blob\r\n" + 
			   		"as\r\n" + 
			   		"  l_key_raw                      raw(32);\r\n" + 
			   		"  l_returnvalue                  blob;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:    encrypt blob\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:    p_key should be 32 characters (256 bits / 8 = 32 bytes)\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     20.01.2011  Created\r\n" + 
			   		"  \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  l_key_raw := utl_raw.cast_to_raw (p_key);\r\n" + 
			   		"  \r\n" + 
			   		"  dbms_lob.createtemporary (l_returnvalue, false);\r\n" + 
			   		"\r\n" + 
			   		"  dbms_crypto.encrypt (l_returnvalue, p_blob, g_encryption_type_aes, l_key_raw);\r\n" + 
			   		"\r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"end encrypt_aes256;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function decrypt_aes256 (p_blob in blob,\r\n" + 
			   		"                         p_key in varchar2) return blob\r\n" + 
			   		"as\r\n" + 
			   		"  l_key_raw                      raw(32);\r\n" + 
			   		"  l_returnvalue                  blob;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:    decrypt blob\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:    p_key should be 32 characters (256 bits / 8 = 32 bytes)\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     20.01.2011  Created\r\n" + 
			   		"  \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  l_key_raw := utl_raw.cast_to_raw (p_key);\r\n" + 
			   		"  \r\n" + 
			   		"  dbms_lob.createtemporary (l_returnvalue, false);\r\n" + 
			   		"\r\n" + 
			   		"  dbms_crypto.decrypt (l_returnvalue, p_blob, g_encryption_type_aes, l_key_raw);\r\n" + 
			   		"\r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"end decrypt_aes256;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"end crypto_util_pkg;\r\n" + 
			   		"/\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"")
			   ,new TestSourceRec(9, "apex_util_pkg", "PACKAGE", "create or replace package apex_util_pkg\r\n" + 
			   		"as\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      package provides general Apex utilities\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  FDL     12.06.2008  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		"\r\n" + 
			   		"  g_apex_null_str                constant varchar2(6) := chr(37) || 'null' || chr(37);\r\n" + 
			   		"  g_apex_undefined_str           constant varchar2(9) := 'undefined';\r\n" + 
			   		"  g_apex_list_separator          constant varchar2(1) := ':';\r\n" + 
			   		"  \r\n" + 
			   		"  -- use these in combination with apex_util.ir_filter\r\n" + 
			   		"  g_ir_filter_equals             constant varchar2(10) := 'EQ';\r\n" + 
			   		"  g_ir_filter_less_than          constant varchar2(10) := 'LT';\r\n" + 
			   		"  g_ir_filter_less_than_or_eq    constant varchar2(10) := 'LTE';\r\n" + 
			   		"  g_ir_filter_greater_than       constant varchar2(10) := 'GT';\r\n" + 
			   		"  g_ir_filter_greater_than_or_eq constant varchar2(10) := 'GTE';\r\n" + 
			   		"  g_ir_filter_like               constant varchar2(10) := 'LIKE';\r\n" + 
			   		"  g_ir_filter_null               constant varchar2(10) := 'N';\r\n" + 
			   		"  g_ir_filter_not_null           constant varchar2(10) := 'NN';\r\n" + 
			   		"\r\n" + 
			   		"  g_ir_reset                     constant varchar2(10) := 'RIR';\r\n" + 
			   		"\r\n" + 
			   		"  -- get page name\r\n" + 
			   		"  function get_page_name (p_application_id in number,\r\n" + 
			   		"                          p_page_id in number) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- get item name for page and item\r\n" + 
			   		"  function get_item_name (p_page_id in number,\r\n" + 
			   		"                          p_item_name in varchar2) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- get page help text\r\n" + 
			   		"  function get_page_help_text (p_application_id in number,\r\n" + 
			   		"                               p_page_id in number) return varchar2;\r\n" + 
			   		" \r\n" + 
			   		"   -- return apex url\r\n" + 
			   		"  function get_apex_url (p_page_id in varchar2,\r\n" + 
			   		"                         p_request in varchar2 := null,\r\n" + 
			   		"                         p_item_names in varchar2 := null,\r\n" + 
			   		"                         p_item_values in varchar2 := null,\r\n" + 
			   		"                         p_debug in varchar2 := null,\r\n" + 
			   		"                         p_application_id in varchar2 := null,\r\n" + 
			   		"                         p_session_id in number := null,\r\n" + 
			   		"                         p_clear_cache in varchar2 := null) return varchar2;\r\n" + 
			   		"                         \r\n" + 
			   		"   -- return apex url (simple syntax)\r\n" + 
			   		"  function get_apex_url_simple (p_page_id in varchar2,\r\n" + 
			   		"                                p_item_name in varchar2 := null,\r\n" + 
			   		"                                p_item_value in varchar2 := null,\r\n" + 
			   		"                                p_request in varchar2 := null) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"    -- get apex url item names\r\n" + 
			   		"  function get_apex_url_item_names (p_page_id in number,\r\n" + 
			   		"                                    p_item_name_array in t_str_array) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"  -- get item values\r\n" + 
			   		"  function get_apex_url_item_values (p_item_value_array in t_str_array) return varchar2;\r\n" + 
			   		"  \r\n" + 
			   		"  -- get query of dynamic lov\r\n" + 
			   		"  function get_dynamic_lov_query (p_application_id in number,\r\n" + 
			   		"                                  p_lov_name in varchar2) return varchar2;\r\n" + 
			   		"                                  \r\n" + 
			   		"  -- set Apex security context\r\n" + 
			   		"  procedure set_apex_security_context (p_schema in varchar2);\r\n" + 
			   		"  \r\n" + 
			   		"  -- setup Apex session context\r\n" + 
			   		"  procedure setup_apex_session_context (p_application_id in number,\r\n" + 
			   		"                                        p_raise_exception_if_invalid in boolean := true);\r\n" + 
			   		"  \r\n" + 
			   		"  -- get string value\r\n" + 
			   		"  function get_str_value (p_str in varchar2) return varchar2;\r\n" + 
			   		" \r\n" + 
			   		"  -- get number value\r\n" + 
			   		"  function get_num_value (p_str in varchar2) return number;\r\n" + 
			   		"\r\n" + 
			   		"  -- get date value\r\n" + 
			   		"  function get_date_value (p_str in varchar2) return date;\r\n" + 
			   		"\r\n" + 
			   		"  -- set Apex item value (string)\r\n" + 
			   		"  procedure set_item (p_page_id in varchar2,\r\n" + 
			   		"                      p_item_name in varchar2,\r\n" + 
			   		"                      p_value in varchar2);\r\n" + 
			   		" \r\n" + 
			   		"  -- set Apex item value (date)\r\n" + 
			   		"  procedure set_date_item (p_page_id in varchar2,\r\n" + 
			   		"                           p_item_name in varchar2,\r\n" + 
			   		"                           p_value in date,\r\n" + 
			   		"                           p_date_format in varchar2 := null);\r\n" + 
			   		"\r\n" + 
			   		"  -- get Apex item value (string)\r\n" + 
			   		"  function get_item (p_page_id in varchar2,\r\n" + 
			   		"                     p_item_name in varchar2,\r\n" + 
			   		"                     p_max_length in number := null) return varchar2;\r\n" + 
			   		" \r\n" + 
			   		"  -- get Apex item value (number)\r\n" + 
			   		"  function get_num_item (p_page_id in varchar2,\r\n" + 
			   		"                         p_item_name in varchar2) return number;\r\n" + 
			   		"\r\n" + 
			   		"  -- get Apex item value (date)\r\n" + 
			   		"  function get_date_item (p_page_id in varchar2,\r\n" + 
			   		"                          p_item_name in varchar2) return date;\r\n" + 
			   		"\r\n" + 
			   		"  -- get multiple item values from page into custom record type\r\n" + 
			   		"  procedure get_items (p_app_id in number,\r\n" + 
			   		"                       p_page_id in number,\r\n" + 
			   		"                       p_target in varchar2,\r\n" + 
			   		"                       p_exclude_items in t_str_array := null);\r\n" + 
			   		" \r\n" + 
			   		"  -- set multiple item values on page based on custom record type\r\n" + 
			   		"  procedure set_items (p_app_id in number,\r\n" + 
			   		"                       p_page_id in number,\r\n" + 
			   		"                       p_source in varchar2,\r\n" + 
			   		"                       p_exclude_items in t_str_array := null);\r\n" + 
			   		"\r\n" + 
			   		"  -- return true if item is in list\r\n" + 
			   		"  function is_item_in_list (p_item in varchar2,\r\n" + 
			   		"                            p_list in apex_application_global.vc_arr2) return boolean;\r\n" + 
			   		"\r\n" + 
			   		"  -- get Apex session value\r\n" + 
			   		"  function get_apex_session_value (p_value_name in varchar2) return varchar2;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"end apex_util_pkg;\r\n" + 
			   		"/\r\n" + 
			   		" \r\n" + 
			   		"")
			   ,new TestSourceRec(10, "apex_util_pkg", "PACKAGE BODY", "create or replace package body apex_util_pkg\r\n" + 
			   		"as\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      package provides general apex utilities\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  FDL     12.06.2008  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"function get_page_name (p_application_id in number,\r\n" + 
			   		"                        p_page_id in number) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue string_util_pkg.t_max_db_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      purpose\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  FDL     12.06.2008  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  begin\r\n" + 
			   		"    select page_name\r\n" + 
			   		"    into l_returnvalue \r\n" + 
			   		"    from apex_application_pages\r\n" + 
			   		"    where application_id = p_application_id\r\n" + 
			   		"    and page_id = p_page_id;\r\n" + 
			   		"  exception\r\n" + 
			   		"    when no_data_found then\r\n" + 
			   		"      l_returnvalue := null;\r\n" + 
			   		"  end;\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_page_name;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_item_name (p_page_id in number,\r\n" + 
			   		"                        p_item_name in varchar2) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue string_util_pkg.t_max_db_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      get item name for page and item\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     10.01.2009  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  l_returnvalue := upper('P' || p_page_id || '_' || p_item_name);\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_item_name;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_page_help_text (p_application_id in number,\r\n" + 
			   		"                             p_page_id in number) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue string_util_pkg.t_max_db_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      purpose\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  FDL     12.06.2008  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  begin\r\n" + 
			   		"    select help_text\r\n" + 
			   		"    into l_returnvalue \r\n" + 
			   		"    from apex_application_pages\r\n" + 
			   		"    where application_id = p_application_id\r\n" + 
			   		"    and page_id = p_page_id;\r\n" + 
			   		"  exception\r\n" + 
			   		"    when no_data_found then\r\n" + 
			   		"      l_returnvalue := null;\r\n" + 
			   		"  end;\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_page_help_text;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_apex_url (p_page_id in varchar2,\r\n" + 
			   		"                       p_request in varchar2 := null,\r\n" + 
			   		"                       p_item_names in varchar2 := null,\r\n" + 
			   		"                       p_item_values in varchar2 := null,\r\n" + 
			   		"                       p_debug in varchar2 := null,\r\n" + 
			   		"                       p_application_id in varchar2 := null,\r\n" + 
			   		"                       p_session_id in number := null,\r\n" + 
			   		"                       p_clear_cache in varchar2 := null) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue                  string_util_pkg.t_max_db_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      return apex url\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      url format: f?p=App:Page:Session:Request:Debug:ClearCache:itemNames:itemValues:PrinterFriendly\r\n" + 
			   		"                App: Application Id\r\n" + 
			   		"                Page: Page Id\r\n" + 
			   		"                Session: Session ID\r\n" + 
			   		"                Request: GET Request (button pressed)\r\n" + 
			   		"                Debug: Whether show debug or not (YES/NO)\r\n" + 
			   		"                ClearCache: Comma delimited string for page(s) for which cache is to be cleared\r\n" + 
			   		"                itemNames: Used to set session state for page items, comma delimited\r\n" + 
			   		"                itemValues: Partner to itemNames, actual session value\r\n" + 
			   		"                PrinterFriendly: Set to YES if page is to be rendered printer friendly\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  FDL     26.03.2008  Created\r\n" + 
			   		"  MBR     12.07.2011  Added clear cache parameter\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  l_returnvalue := 'f?p=' || nvl(p_application_id, v('APP_ID')) \r\n" + 
			   		"                          || ':'|| p_page_id \r\n" + 
			   		"                          || ':' || nvl(p_session_id, v('APP_SESSION'))\r\n" + 
			   		"                          || ':' || p_request\r\n" + 
			   		"                          || ':' || nvl(p_debug, 'NO')\r\n" + 
			   		"                          || ':' || p_clear_cache\r\n" + 
			   		"                          || ':' || p_item_names\r\n" + 
			   		"                          || ':' || utl_url.escape(p_item_values)\r\n" + 
			   		"                          || ':';\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_apex_url;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_apex_url_simple (p_page_id in varchar2,\r\n" + 
			   		"                              p_item_name in varchar2 := null,\r\n" + 
			   		"                              p_item_value in varchar2 := null,\r\n" + 
			   		"                              p_request in varchar2 := null) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue                  string_util_pkg.t_max_db_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      return apex url (simple syntax)\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      assumes only one parameter, and prefixes the parameter name with page number\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     03.08.2010  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  l_returnvalue := 'f?p=' || v('APP_ID') \r\n" + 
			   		"                          || ':'|| p_page_id \r\n" + 
			   		"                          || ':' || v('APP_SESSION')\r\n" + 
			   		"                          || ':' || p_request\r\n" + 
			   		"                          || ':' || 'NO'\r\n" + 
			   		"                          || ':'\r\n" + 
			   		"                          || ':' || case when p_item_name is not null then 'P' || p_page_id || '_' || p_item_name else null end\r\n" + 
			   		"                          || ':' || utl_url.escape(p_item_value)\r\n" + 
			   		"                          || ':';\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_apex_url_simple;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_apex_url_item_names (p_page_id in number,\r\n" + 
			   		"                                  p_item_name_array in t_str_array) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue                  string_util_pkg.t_max_db_varchar2;\r\n" + 
			   		"  l_str                          string_util_pkg.t_max_db_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      get item name\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  THH     28.05.2008  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  for i in 1..p_item_name_array.count loop\r\n" + 
			   		"  \r\n" + 
			   		"    l_str := 'P' || p_page_id || '_' || p_item_name_array(i);\r\n" + 
			   		"    l_returnvalue := string_util_pkg.add_item_to_list(l_str, l_returnvalue, ',');\r\n" + 
			   		"  end loop;\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_apex_url_item_names;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_apex_url_item_values (p_item_value_array in t_str_array) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue                  string_util_pkg.t_max_db_varchar2;\r\n" + 
			   		"  l_str                          string_util_pkg.t_max_db_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      get item values\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  THH     28.05.2008  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  for i in 1..p_item_value_array.count loop\r\n" + 
			   		"\r\n" + 
			   		"    l_str := p_item_value_array(i);\r\n" + 
			   		"    l_returnvalue := string_util_pkg.add_item_to_list(l_str, l_returnvalue, ',');\r\n" + 
			   		"    \r\n" + 
			   		"  end loop;\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;                          \r\n" + 
			   		"\r\n" + 
			   		"end get_apex_url_item_values;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_dynamic_lov_query (p_application_id in number,\r\n" + 
			   		"                                p_lov_name in varchar2) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue string_util_pkg.t_max_pl_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      get query of dynamic lov\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  FDL     08.07.2008  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  begin\r\n" + 
			   		"  \r\n" + 
			   		"    select list_of_values_query\r\n" + 
			   		"    into l_returnvalue\r\n" + 
			   		"    from apex_application_lovs\r\n" + 
			   		"    where application_id = p_application_id\r\n" + 
			   		"    and list_of_values_name = p_lov_name;\r\n" + 
			   		"    \r\n" + 
			   		"  exception\r\n" + 
			   		"    when no_data_found then\r\n" + 
			   		"      l_returnvalue := null;\r\n" + 
			   		"  end;\r\n" + 
			   		"  \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"  \r\n" + 
			   		"end get_dynamic_lov_query;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"procedure set_apex_security_context (p_schema in varchar2)\r\n" + 
			   		"as\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:    set Apex security context\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:    to be able to run Apex APIs that require a context (security group ID) to be set\r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     04.12.2009  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  wwv_flow_api.set_security_group_id(apex_util.find_security_group_id(p_schema));\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"end set_apex_security_context;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"procedure setup_apex_session_context (p_application_id in number,\r\n" + 
			   		"                                      p_raise_exception_if_invalid in boolean := true)\r\n" + 
			   		"as\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"  \r\n" + 
			   		"  Purpose:      setup Apex session context\r\n" + 
			   		"  \r\n" + 
			   		"  Remarks:      required before calling packages via the URL, outside the Apex framework\r\n" + 
			   		"  \r\n" + 
			   		"  Who      Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     20.10.2009  Created\r\n" + 
			   		"  MBR     22.12.2012  Added fix for breaking change in Apex 4.2, see http://code.google.com/p/plsql-utils/issues/detail?id=18\r\n" + 
			   		"  MBR     22.12.2012  Added parameter to specify if no valid session should raise an exception\r\n" + 
			   		"  \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  apex_application.g_flow_id := p_application_id;\r\n" + 
			   		"  \r\n" + 
			   		"  if apex_custom_auth.is_session_valid then\r\n" + 
			   		"\r\n" + 
			   		"    apex_custom_auth.set_session_id (apex_custom_auth.get_session_id_from_cookie);\r\n" + 
			   		"    apex_custom_auth.set_user (apex_custom_auth.get_username);\r\n" + 
			   		"    wwv_flow_api.set_security_group_id (apex_custom_auth.get_security_group_id);\r\n" + 
			   		"    \r\n" + 
			   		"  else\r\n" + 
			   		"    if p_raise_exception_if_invalid then\r\n" + 
			   		"      raise_application_error (-20000, 'Session not valid.');\r\n" + 
			   		"    end if;\r\n" + 
			   		"  end if;\r\n" + 
			   		"\r\n" + 
			   		"end setup_apex_session_context;\r\n" + 
			   		"\r\n" + 
			   		" \r\n" + 
			   		"function get_str_value (p_str in varchar2) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue string_util_pkg.t_max_pl_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:    get string value\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:    \r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     03.05.2010  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  if p_str in (g_apex_null_str, g_apex_undefined_str) then\r\n" + 
			   		"    l_returnvalue := null;\r\n" + 
			   		"  else\r\n" + 
			   		"    l_returnvalue := p_str;\r\n" + 
			   		"  end if;\r\n" + 
			   		"  \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"end get_str_value;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_num_value (p_str in varchar2) return number\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue number;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:    get number value\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:    \r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     03.05.2010  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  if p_str in (g_apex_null_str, g_apex_undefined_str) then\r\n" + 
			   		"    l_returnvalue := null;\r\n" + 
			   		"  else\r\n" + 
			   		"    -- assuming the NLS parameters are set correctly, we do NOT specify decimal or thousand separator\r\n" + 
			   		"    l_returnvalue := string_util_pkg.str_to_num (p_str, null, null);\r\n" + 
			   		"  end if;\r\n" + 
			   		"  \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"end get_num_value;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_date_value (p_str in varchar2) return date\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue date;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"\r\n" + 
			   		"  Purpose:    get date value\r\n" + 
			   		"\r\n" + 
			   		"  Remarks:    \r\n" + 
			   		"\r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  -------------------------------------\r\n" + 
			   		"  MBR     03.05.2010  Created\r\n" + 
			   		"\r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  if p_str in (g_apex_null_str, g_apex_undefined_str) then\r\n" + 
			   		"    l_returnvalue := null;\r\n" + 
			   		"  else\r\n" + 
			   		"    l_returnvalue := string_util_pkg.parse_date (p_str);\r\n" + 
			   		"  end if;\r\n" + 
			   		"  \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"end get_date_value;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"procedure set_item (p_page_id in varchar2,\r\n" + 
			   		"                    p_item_name in varchar2,\r\n" + 
			   		"                    p_value in varchar2) \r\n" + 
			   		"as\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      set Apex item value (string)\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     02.11.2010  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		"  apex_util.set_session_state ('P' || p_page_id || '_' || upper(p_item_name), p_value);\r\n" + 
			   		" \r\n" + 
			   		"end set_item;\r\n" + 
			   		" \r\n" + 
			   		"\r\n" + 
			   		"procedure set_date_item (p_page_id in varchar2,\r\n" + 
			   		"                         p_item_name in varchar2,\r\n" + 
			   		"                         p_value in date,\r\n" + 
			   		"                         p_date_format in varchar2 := null) \r\n" + 
			   		"as\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      set Apex item value (date)\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     02.11.2010  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		"  apex_util.set_session_state ('P' || p_page_id || '_' || upper(p_item_name), to_char(p_value, nvl(p_date_format, date_util_pkg.g_date_fmt_date_hour_min)));\r\n" + 
			   		" \r\n" + 
			   		"end set_date_item;\r\n" + 
			   		"\r\n" + 
			   		" \r\n" + 
			   		"function get_item (p_page_id in varchar2,\r\n" + 
			   		"                   p_item_name in varchar2,\r\n" + 
			   		"                   p_max_length in number := null) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue string_util_pkg.t_max_pl_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      get Apex item value (string)\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     02.11.2010  Created\r\n" + 
			   		"  MBR     01.12.2010  Added parameter for max length\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		"  l_returnvalue := get_str_value (apex_util.get_session_state ('P' || p_page_id || '_' || upper(p_item_name)));\r\n" + 
			   		"  \r\n" + 
			   		"  if p_max_length is not null then\r\n" + 
			   		"    l_returnvalue := substr(l_returnvalue, 1, p_max_length);\r\n" + 
			   		"  end if;\r\n" + 
			   		"\r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_item;\r\n" + 
			   		" \r\n" + 
			   		" \r\n" + 
			   		"function get_num_item (p_page_id in varchar2,\r\n" + 
			   		"                       p_item_name in varchar2) return number\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue number;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      get Apex item value (number)\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     02.11.2010  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  l_returnvalue := get_num_value (apex_util.get_session_state ('P' || p_page_id || '_' || upper(p_item_name)));\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_num_item;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_date_item (p_page_id in varchar2,\r\n" + 
			   		"                        p_item_name in varchar2) return date\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue date;\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      get Apex item value (date)\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     02.11.2010  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  l_returnvalue := get_date_value (apex_util.get_session_state ('P' || p_page_id || '_' || upper(p_item_name)));\r\n" + 
			   		" \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		" \r\n" + 
			   		"end get_date_item;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"procedure get_items (p_app_id in number,\r\n" + 
			   		"                     p_page_id in number,\r\n" + 
			   		"                     p_target in varchar2,\r\n" + 
			   		"                     p_exclude_items in t_str_array := null) \r\n" + 
			   		"as\r\n" + 
			   		"\r\n" + 
			   		"  cursor l_item_cursor\r\n" + 
			   		"  is\r\n" + 
			   		"  select item_name, substr(lower(item_name), length('p' || p_page_id || '_') +1 ) as field_name,\r\n" + 
			   		"    display_as\r\n" + 
			   		"  from apex_application_page_items\r\n" + 
			   		"  where application_id = p_app_id\r\n" + 
			   		"  and page_id = p_page_id\r\n" + 
			   		"  and item_name not in (select upper(column_value) from table(p_exclude_items))\r\n" + 
			   		"  and display_as not like '%does not save state%'\r\n" + 
			   		"  order by item_name;\r\n" + 
			   		"\r\n" + 
			   		"  l_sql                          string_util_pkg.t_max_pl_varchar2;\r\n" + 
			   		"  l_cursor                       pls_integer;\r\n" + 
			   		"  l_rows                         pls_integer;\r\n" + 
			   		"\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      get multiple item values from page into custom record type\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      this procedure grabs all the values from a page, so we don't have to write code to retrieve each item separately\r\n" + 
			   		"                since a PL/SQL function cannot return a dynamic type (%ROWTYPE and PL/SQL records are not supported by ANYDATA/ANYTYPE),\r\n" + 
			   		"                  we must populate a global package variable as a workaround\r\n" + 
			   		"                the global package variable (specified using the p_target parameter) must have fields matching the item names on the page\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     15.02.2011  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		"  for l_rec in l_item_cursor loop\r\n" + 
			   		"    l_sql := l_sql || '  ' || p_target || '.' || l_rec.field_name || ' := :b' || l_item_cursor%rowcount || ';' || chr(10);\r\n" + 
			   		"  end loop;\r\n" + 
			   		"  \r\n" + 
			   		"  l_sql := 'begin' || chr(10) || l_sql || 'end;';\r\n" + 
			   		"  \r\n" + 
			   		"  --debug_pkg.printf('sql = %1', l_sql);\r\n" + 
			   		"  \r\n" + 
			   		"  begin\r\n" + 
			   		"    l_cursor := dbms_sql.open_cursor;\r\n" + 
			   		"    dbms_sql.parse (l_cursor, l_sql, dbms_sql.native);\r\n" + 
			   		"    \r\n" + 
			   		"    for l_rec in l_item_cursor loop\r\n" + 
			   		"      if l_rec.display_as like '%Date Picker%' then\r\n" + 
			   		"        dbms_sql.bind_variable (l_cursor, ':b' || l_item_cursor%rowcount, get_date_value(apex_util.get_session_state(l_rec.item_name)));\r\n" + 
			   		"      else\r\n" + 
			   		"        dbms_sql.bind_variable (l_cursor, ':b' || l_item_cursor%rowcount, get_str_value(apex_util.get_session_state(l_rec.item_name)));\r\n" + 
			   		"      end if;\r\n" + 
			   		"    end loop;\r\n" + 
			   		"    \r\n" + 
			   		"    l_rows := dbms_sql.execute (l_cursor);\r\n" + 
			   		"    dbms_sql.close_cursor (l_cursor);\r\n" + 
			   		"  exception\r\n" + 
			   		"    when others then\r\n" + 
			   		"      if dbms_sql.is_open (l_cursor) then\r\n" + 
			   		"        dbms_sql.close_cursor (l_cursor);\r\n" + 
			   		"      end if;\r\n" + 
			   		"      raise;\r\n" + 
			   		"  end;\r\n" + 
			   		" \r\n" + 
			   		"end get_items;\r\n" + 
			   		" \r\n" + 
			   		" \r\n" + 
			   		"procedure set_items (p_app_id in number,\r\n" + 
			   		"                     p_page_id in number,\r\n" + 
			   		"                     p_source in varchar2,\r\n" + 
			   		"                     p_exclude_items in t_str_array := null) \r\n" + 
			   		"as\r\n" + 
			   		"\r\n" + 
			   		"  cursor l_item_cursor\r\n" + 
			   		"  is\r\n" + 
			   		"  select item_name, substr(lower(item_name), length('p' || p_page_id || '_') +1 ) as field_name,\r\n" + 
			   		"    display_as\r\n" + 
			   		"  from apex_application_page_items\r\n" + 
			   		"  where application_id = p_app_id\r\n" + 
			   		"  and page_id = p_page_id\r\n" + 
			   		"  and item_name not in (select upper(column_value) from table(p_exclude_items))\r\n" + 
			   		"  and display_as not like '%does not save state%'\r\n" + 
			   		"  order by item_name;\r\n" + 
			   		"\r\n" + 
			   		"  l_sql                          string_util_pkg.t_max_pl_varchar2;\r\n" + 
			   		"\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      set multiple item values on page based on custom record type\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     15.02.2011  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		"  for l_rec in l_item_cursor loop\r\n" + 
			   		"    l_sql := l_sql || '  apex_util.set_session_state(''' || l_rec.item_name || ''', ' || p_source || '.' || l_rec.field_name || ');' || chr(10);\r\n" + 
			   		"  end loop;\r\n" + 
			   		"  \r\n" + 
			   		"  l_sql := 'begin' || chr(10) || l_sql || 'end;';\r\n" + 
			   		"  \r\n" + 
			   		"  execute immediate l_sql;\r\n" + 
			   		" \r\n" + 
			   		"end set_items;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function is_item_in_list (p_item in varchar2,\r\n" + 
			   		"                          p_list in apex_application_global.vc_arr2) return boolean\r\n" + 
			   		"as\r\n" + 
			   		"  l_index       binary_integer;\r\n" + 
			   		"  l_returnvalue boolean := false;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      return true if specified item exists in list \r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     09.07.2011  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  l_index := p_list.first;\r\n" + 
			   		"  while (l_index is not null) loop\r\n" + 
			   		"    if p_list(l_index) = p_item then\r\n" + 
			   		"      l_returnvalue := true;\r\n" + 
			   		"      exit;\r\n" + 
			   		"    end if;\r\n" + 
			   		"    l_index := p_list.next(l_index);\r\n" + 
			   		"  end loop;\r\n" + 
			   		"\r\n" + 
			   		"  return l_returnvalue; \r\n" + 
			   		"\r\n" + 
			   		"end is_item_in_list;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"function get_apex_session_value (p_value_name in varchar2) return varchar2\r\n" + 
			   		"as\r\n" + 
			   		"  l_returnvalue string_util_pkg.t_max_pl_varchar2;\r\n" + 
			   		"begin\r\n" + 
			   		"\r\n" + 
			   		"  /*\r\n" + 
			   		"  \r\n" + 
			   		"  Purpose:      get Apex session value\r\n" + 
			   		"  \r\n" + 
			   		"  Remarks:      if a package is called outside the Apex framework (but in a valid session -- see setup_apex_session_context),\r\n" + 
			   		"                the session values are not available via apex_util.get_session_state or the V function, see http://forums.oracle.com/forums/thread.jspa?threadID=916301\r\n" + 
			   		"                a workaround is to use the \"do_substitutions\" function, see http://apex-smb.blogspot.com/2009/07/apexapplicationdosubstitutions.html\r\n" + 
			   		"  \r\n" + 
			   		"  Who      Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     26.01.2010  Created\r\n" + 
			   		"  \r\n" + 
			   		"  */\r\n" + 
			   		"\r\n" + 
			   		"  l_returnvalue := apex_application.do_substitutions(chr(38) || upper(p_value_name) || '.');\r\n" + 
			   		"  \r\n" + 
			   		"  return l_returnvalue;\r\n" + 
			   		"\r\n" + 
			   		"end get_apex_session_value;\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"end apex_util_pkg;\r\n" + 
			   		"/\r\n" + 
			   		"")
			   ,new TestSourceRec(11, "datapump_util_pkg", "PACKAGE", "create or replace package datapump_util_pkg\r\n" + 
			   		"as\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      Package contains Data Pump utilities\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     04.11.2011  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		" \r\n" + 
			   		"  -- export (current) schema to file\r\n" + 
			   		"  procedure export_schema_to_file (p_directory_name in varchar2,\r\n" + 
			   		"                                   p_file_name in varchar2 := null,\r\n" + 
			   		"                                   p_version in varchar2 := null,\r\n" + 
			   		"                                   p_log_message in varchar2 := null,\r\n" + 
			   		"                                   p_compress in boolean := false);\r\n" + 
			   		" \r\n" + 
			   		"  -- import schema from file\r\n" + 
			   		"  procedure import_schema_from_file (p_directory_name in varchar2,\r\n" + 
			   		"                                     p_file_name in varchar2,\r\n" + 
			   		"                                     p_log_file_name in varchar2 := null,\r\n" + 
			   		"                                     p_remap_from_schema in varchar2 := null,\r\n" + 
			   		"                                     p_remap_to_schema in varchar2 := null,\r\n" + 
			   		"                                     p_table_data_only in boolean := false);\r\n" + 
			   		" \r\n" + 
			   		" \r\n" + 
			   		"end datapump_util_pkg;\r\n" + 
			   		"/\r\n" + 
			   		"\r\n" + 
			   		"")
			   ,new TestSourceRec(12, "datapump_util_pkg", "PACKAGE BODY", "create or replace package body datapump_util_pkg\r\n" + 
			   		"as\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      Package contains Data Pump utilities\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     04.11.2011  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		" \r\n" + 
			   		"procedure export_schema_to_file (p_directory_name in varchar2,\r\n" + 
			   		"                                 p_file_name in varchar2 := null,\r\n" + 
			   		"                                 p_version in varchar2 := null,\r\n" + 
			   		"                                 p_log_message in varchar2 := null,\r\n" + 
			   		"                                 p_compress in boolean := false) \r\n" + 
			   		"as\r\n" + 
			   		"  l_job_handle                   number;\r\n" + 
			   		"  l_job_status                   varchar2(30); -- COMPLETED or STOPPED\r\n" + 
			   		"  l_file_name                    varchar2(2000) := nvl(p_file_name, 'export_' || lower(user) || '_' || to_char(sysdate, 'yyyymmddhh24miss'));\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      export (current) schema to file\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      the file name, if specified, should not include the extension, as it will be used for both dump and log files\r\n" + 
			   		"                specify the p_version parameter if intending to import into an older database (such as '10.2' for XE)\r\n" + 
			   		" \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     04.11.2011  Created\r\n" + 
			   		"  MBR     07.08.2012  Added parameter to specify compression\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		" \r\n" + 
			   		"  l_job_handle := dbms_datapump.open ('EXPORT',  'SCHEMA', version => nvl(p_version, 'COMPATIBLE'));\r\n" + 
			   		"\r\n" + 
			   		"  dbms_datapump.add_file (l_job_handle, l_file_name || '.dmp', p_directory_name);\r\n" + 
			   		"  dbms_datapump.add_file (l_job_handle, l_file_name || '.log', p_directory_name, filetype => dbms_datapump.ku$_file_type_log_file);\r\n" + 
			   		"\r\n" + 
			   		"  -- may set additional filters, not neccessary for full export of current schema\r\n" + 
			   		"  -- see http://forums.oracle.com/forums/thread.jspa?messageID=9726231\r\n" + 
			   		"  --dbms_datapump.metadata_filter (l_job_handle, 'SCHEMA_LIST', user);\r\n" + 
			   		"  \r\n" + 
			   		"  -- compression (note: p_version should be at least 11.1 to support this)\r\n" + 
			   		"  if p_compress then\r\n" + 
			   		"    dbms_datapump.set_parameter (l_job_handle, 'COMPRESSION', 'ALL');\r\n" + 
			   		"  end if;\r\n" + 
			   		"   \r\n" + 
			   		"  dbms_datapump.start_job (l_job_handle);\r\n" + 
			   		" \r\n" + 
			   		"  if p_log_message is not null then\r\n" + 
			   		"    dbms_datapump.log_entry (l_job_handle, p_log_message);\r\n" + 
			   		"  end if;\r\n" + 
			   		"\r\n" + 
			   		"  dbms_datapump.wait_for_job (l_job_handle, l_job_status);\r\n" + 
			   		" \r\n" + 
			   		"  dbms_datapump.detach (l_job_handle);\r\n" + 
			   		"\r\n" + 
			   		"  debug_pkg.printf('Job status = %1, file name = %2', l_job_status, l_file_name);\r\n" + 
			   		"\r\n" + 
			   		"  if l_job_status not in ('COMPLETED', 'STOPPED') then\r\n" + 
			   		"    raise_application_error (-20000, string_util_pkg.get_str('The data pump job exited with status = %1 (file name = %2).', l_job_status, l_file_name));\r\n" + 
			   		"  end if;\r\n" + 
			   		" \r\n" + 
			   		"end export_schema_to_file;\r\n" + 
			   		" \r\n" + 
			   		" \r\n" + 
			   		"procedure import_schema_from_file (p_directory_name in varchar2,\r\n" + 
			   		"                                   p_file_name in varchar2,\r\n" + 
			   		"                                   p_log_file_name in varchar2 := null,\r\n" + 
			   		"                                   p_remap_from_schema in varchar2 := null,\r\n" + 
			   		"                                   p_remap_to_schema in varchar2 := null,\r\n" + 
			   		"                                   p_table_data_only in boolean := false) \r\n" + 
			   		"as\r\n" + 
			   		"\r\n" + 
			   		"  l_job_handle                   number;\r\n" + 
			   		"  l_job_status                   varchar2(30); -- COMPLETED or STOPPED\r\n" + 
			   		"  \r\n" + 
			   		"  l_from_schema                  varchar2(30) := nvl(p_remap_from_schema, user);\r\n" + 
			   		"  l_to_schema                    varchar2(30) := nvl(p_remap_to_schema, user);\r\n" + 
			   		"\r\n" + 
			   		"  l_log_file_name                varchar2(2000) := nvl(p_log_file_name, p_file_name || '_import_' || to_char(sysdate, 'yyyymmddhh24miss') || '.log');\r\n" + 
			   		"\r\n" + 
			   		"begin\r\n" + 
			   		" \r\n" + 
			   		"  /*\r\n" + 
			   		" \r\n" + 
			   		"  Purpose:      import schema from file\r\n" + 
			   		" \r\n" + 
			   		"  Remarks:      \r\n" + 
			   		"  \r\n" + 
			   		"  Who     Date        Description\r\n" + 
			   		"  ------  ----------  --------------------------------\r\n" + 
			   		"  MBR     04.11.2011  Created\r\n" + 
			   		" \r\n" + 
			   		"  */\r\n" + 
			   		"  \r\n" + 
			   		"  l_job_handle := dbms_datapump.open ('IMPORT', 'SCHEMA');\r\n" + 
			   		"\r\n" + 
			   		"  dbms_datapump.add_file (l_job_handle, p_file_name, p_directory_name);\r\n" + 
			   		"  dbms_datapump.add_file (l_job_handle, l_log_file_name, p_directory_name, filetype => dbms_datapump.ku$_file_type_log_file);\r\n" + 
			   		"\r\n" + 
			   		"  -- see http://download.oracle.com/docs/cd/B28359_01/server.111/b28319/dp_import.htm\r\n" + 
			   		"  \r\n" + 
			   		"  -- \"If your dump file set does not contain the metadata necessary to create a schema, or if you do not have privileges, then the target schema must be created before the import operation is performed.\"\r\n" + 
			   		"  -- \"Nonprivileged users can perform schema remaps only if their schema is the target schema of the remap.\"\r\n" + 
			   		"\r\n" + 
			   		"  if l_from_schema <> l_to_schema then\r\n" + 
			   		"    dbms_datapump.metadata_remap (l_job_handle, 'REMAP_SCHEMA', l_from_schema, l_to_schema);\r\n" + 
			   		"  end if;\r\n" + 
			   		"  \r\n" + 
			   		"  -- workaround for performance bug in 10.2, see http://forums.oracle.com/forums/thread.jspa?threadID=401886\r\n" + 
			   		"  dbms_datapump.metadata_filter (l_job_handle, 'EXCLUDE_PATH_LIST', '''STATISTICS'''); -- note the double quotes\r\n" + 
			   		"\r\n" + 
			   		"  if p_table_data_only then\r\n" + 
			   		"    dbms_datapump.metadata_filter (l_job_handle, 'INCLUDE_PATH_LIST', '''TABLE'''); -- note the double quotes\r\n" + 
			   		"    -- TODO: investigate the \"TABLE_EXISTS_ACTION\" parameter\r\n" + 
			   		"    -- not sure what the context was for the TODO, but we use it to clear down tables before importing data.\r\n" + 
			   		"    -- Usage is dbms_datapump.set_parameter(l_job_handle, 'TABLE_EXISTS_ACTION', 'TRUNCATE');\r\n" + 
			   		"  end if;\r\n" + 
			   		"  \r\n" + 
			   		"  dbms_datapump.start_job (l_job_handle);\r\n" + 
			   		" \r\n" + 
			   		"  dbms_datapump.wait_for_job (l_job_handle, l_job_status);\r\n" + 
			   		" \r\n" + 
			   		"  dbms_datapump.detach (l_job_handle);\r\n" + 
			   		"\r\n" + 
			   		"  if l_job_status in ('COMPLETED', 'STOPPED') then\r\n" + 
			   		"    debug_pkg.printf ('SUCCESS: Job status %1, log file name %2', l_job_status, l_log_file_name);\r\n" + 
			   		"  else\r\n" + 
			   		"    debug_pkg.printf ('WARNING: The job exited with status %1, log file name %2', l_job_status, l_log_file_name);\r\n" + 
			   		"  end if;\r\n" + 
			   		" \r\n" + 
			   		"end import_schema_from_file;\r\n" + 
			   		" \r\n" + 
			   		"\r\n" + 
			   		"end datapump_util_pkg;\r\n" + 
			   		"/\r\n" + 
			   		" \r\n" + 
			   		"\r\n" + 
			   		"\r\n" + 
			   		"")
		};
	}
	
	@Override
	public Object[] getElements(Object arg0) {
		// Returns all the players in the specified team
		if (this.list == null) {
			this.list = new SearchResult[0];
		}
		return list;
	}
	
	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		if (arg2 != null) {
			// the following search strings are hard coded to return some results, use those to test!
			switch (((SearchData)arg2).getSearchString().toLowerCase()) {
			case "data  pump":
			case "data pump":
			case "datapump":
				this.list = new SearchResult[]{
						new SearchResult(allSources[10].getSourceId(), allSources[10].getSourceName(), allSources[10].getSourceType(), 1, 2)
						,new SearchResult(allSources[11].getSourceId(), allSources[11].getSourceName(), allSources[11].getSourceType(), 1, 2)
				};
				break;
			case "package":
				this.list = new SearchResult[]{
						 new SearchResult(allSources[0].getSourceId(), allSources[0].getSourceName(), allSources[0].getSourceType(), 1, 0)
						,new SearchResult(allSources[1].getSourceId(), allSources[1].getSourceName(), allSources[1].getSourceType(), 1, 0)
						,new SearchResult(allSources[2].getSourceId(), allSources[2].getSourceName(), allSources[2].getSourceType(), 1, 0)
						,new SearchResult(allSources[3].getSourceId(), allSources[3].getSourceName(), allSources[3].getSourceType(), 1, 0)
						,new SearchResult(allSources[4].getSourceId(), allSources[4].getSourceName(), allSources[4].getSourceType(), 2, 0)
						,new SearchResult(allSources[5].getSourceId(), allSources[5].getSourceName(), allSources[5].getSourceType(), 2, 0)
						,new SearchResult(allSources[6].getSourceId(), allSources[6].getSourceName(), allSources[6].getSourceType(), 2, 1)
						,new SearchResult(allSources[7].getSourceId(), allSources[7].getSourceName(), allSources[7].getSourceType(), 2, 1)
						,new SearchResult(allSources[8].getSourceId(), allSources[8].getSourceName(), allSources[8].getSourceType(), 2, 1)
						,new SearchResult(allSources[9].getSourceId(), allSources[9].getSourceName(), allSources[9].getSourceType(), 2, 1)
						,new SearchResult(allSources[10].getSourceId(), allSources[10].getSourceName(), allSources[10].getSourceType(), 1, 1)
						,new SearchResult(allSources[11].getSourceId(), allSources[11].getSourceName(), allSources[11].getSourceType(), 1, 1)
				};
				break;
			case "package body":
				this.list = new SearchResult[]{
						 new SearchResult(allSources[1].getSourceId(), allSources[1].getSourceName(), allSources[1].getSourceType(), 1, 0)
						,new SearchResult(allSources[3].getSourceId(), allSources[3].getSourceName(), allSources[3].getSourceType(), 1, 0)
						,new SearchResult(allSources[5].getSourceId(), allSources[5].getSourceName(), allSources[5].getSourceType(), 2, 0)
						,new SearchResult(allSources[7].getSourceId(), allSources[7].getSourceName(), allSources[7].getSourceType(), 2, 1)
						,new SearchResult(allSources[9].getSourceId(), allSources[9].getSourceName(), allSources[9].getSourceType(), 2, 1)
						,new SearchResult(allSources[11].getSourceId(), allSources[11].getSourceName(), allSources[11].getSourceType(), 1, 1)
				};
				break;

			default:
				break;
			}
		} else {
			this.list = new SearchResult[0];
		}
	}
	
	/*TODO @Anoop: move this to SearchContentProvider, so it can be overridden here
	public String getText(SearchResult res) {
		return ConnectionSingleton.getInstance().getText(res.getSourceId());
	}*/
	
	@Override
	public String getText(SearchResult res) {
		int sourceId = res.getSourceId();
		return allSources[sourceId-1].getContent();
	}
}
 