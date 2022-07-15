#! /usr/bin/perl
use Net::FTP;
use Time::localtime;
@files = </var/backup_ftp/*>;
$fileSize = scalar(@files);
$counter;
$now = ctime();

print "Time: $now.\nCopying nightly backup to backup server\n";
$ftpobj = Net::FTP -> new ("backup160.onlinehome-server.com");
$ftpobj -> login("bak14893","y4d.57b");
print "Successfully logged in\n";
$ftpobj -> cwd ("/backup");
# remove all white space in date
$now =~ s/ /_/g;
$ftpobj -> mkdir ("$now");
$ftpobj -> cwd ("$now");
print "Uploading to directory: /backup/$now\n"; 
$ftpobj -> binary;
for ($counter = 0; $counter < $fileSize; $counter++) {
  print "Uploading: $files[$counter]\n";
  $ftpobj -> put ($files[$counter]);
}
$ftpobj -> quit;
print "Disconnected. Backup complete\n";
