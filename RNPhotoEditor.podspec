require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "RNPhotoEditor"
  s.version      = package['version']
  s.summary      = package['description']
  s.description  = package["description"]
  s.homepage     = "n/a"
  s.license      = package['license']
  s.author       = package['author']
  s.platform     = :ios, "9.0"
  s.ios.deployment_target = '9.0'
  s.libraries        = 'z'
  s.preserve_paths = '*.js'
  s.source       = { :git => "https://github.com/homeeondemand/react-native-photo-editor.git", :tag => "master" }
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true
  s.dependency "React"
  s.dependency "iOSPhotoEditor"
  s.static_framework = true
end
