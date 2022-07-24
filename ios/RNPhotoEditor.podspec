require 'json'

package = JSON.parse(File.read(File.join(__dir__, '../package.json')))

Pod::Spec.new do |s|
  s.name                   = 'RNPhotoEditor'
  s.version                = package['version']
  s.summary                = package['description']
  s.description            = package['description']
  s.homepage               = package['homepage']
  s.license                = package['license']
  s.author                 = package['author']
  s.source                 = { :git => 'https://github.com/Kegmil/react-native-photo-editor.git', :tag => s.version }

  s.platform               = :ios, '9.0'
  s.ios.deployment_target  = '8.0'

  s.preserve_paths         = 'LICENSE', 'package.json'
  s.source_files           = '**/*.{h,m}'
  s.dependency             'React'
  s.dependency             'iOSPhotoEditor'
end