var platform = {
  print : function(output) {
    java.lang.System.out.println(output);
  },
  load : function(filename) {
    var reader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(new java.io.File(filename))));

	var line;
	var total = "";
	while ((line = reader.readLine()) != null) {
	  total += line;
	}

	return total;
  }
}