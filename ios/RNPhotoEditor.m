
#import "RNPhotoEditor.h"

@implementation RNPhotoEditor

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()


RCT_EXPORT_METHOD(Edit:(nonnull NSDictionary *)props onDone:(RCTResponseSenderBlock)onDone onCancel:(RCTResponseSenderBlock)onCancel) {
    
    dispatch_async(dispatch_get_main_queue(), ^{
        NSString *path = [props objectForKey: @"path"];
        
        PhotoEditorViewController *photoEditor = [[PhotoEditorViewController alloc] initWithNibName:@"PhotoEditorViewController" bundle: nil];
        
        UIImage *image = [UIImage imageWithContentsOfFile:path];
        photoEditor.image = image;
        
        id<UIApplicationDelegate> app = [[UIApplication sharedApplication] delegate];
        [((UINavigationController*) app.window.rootViewController) presentViewController:photoEditor animated:YES completion:nil];
    });
}

@end
  
