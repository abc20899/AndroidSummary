### load方法分析

```
 public DrawableTypeRequest<String> load(String string) {
        return (DrawableTypeRequest<String>) fromString().load(string);
    }
```
返回一个DrawableTypeRequest对象

```
public class DrawableTypeRequest<ModelType> extends 
DrawableRequestBuilder<ModelType> implements DownloadOptions {}
```

```
public class DrawableRequestBuilder<ModelType>
        extends GenericRequestBuilder<ModelType, ImageVideoWrapper, GifBitmapWrapper, GlideDrawable>
        implements BitmapOptions, DrawableOptions {
```

GenericRequestBuilder类构建初始化的参数

```
public class GenericRequestBuilder<ModelType, DataType, ResourceType, TranscodeType> implements Cloneable {
    protected final Class<ModelType> modelClass;
    protected final Context context;
    protected final Glide glide;
    protected final Class<TranscodeType> transcodeClass;
    protected final RequestTracker requestTracker;
    protected final Lifecycle lifecycle;
    private ChildLoadProvider<ModelType, DataType, ResourceType, TranscodeType> loadProvider;

    private ModelType model;
    private Key signature = EmptySignature.obtain();
    // model may occasionally be null, so to enforce that load() was called, set a boolean rather than relying on model
    // not to be null.
    private boolean isModelSet;
    private int placeholderId;
    private int errorId;
    private RequestListener<? super ModelType, TranscodeType> requestListener;
    private Float thumbSizeMultiplier;
    private GenericRequestBuilder<?, ?, ?, TranscodeType> thumbnailRequestBuilder;
    private Float sizeMultiplier = 1f;
    private Drawable placeholderDrawable;
    private Drawable errorPlaceholder;
    private Priority priority = null;
    private boolean isCacheable = true;
    private GlideAnimationFactory<TranscodeType> animationFactory = NoAnimation.getFactory();
    private int overrideHeight = -1;
    private int overrideWidth = -1;
    private DiskCacheStrategy diskCacheStrategy = DiskCacheStrategy.RESULT;
    private Transformation<ResourceType> transformation = UnitTransformation.get();
    private boolean isTransformationSet;
    private boolean isThumbnailBuilt;
    private Drawable fallbackDrawable;
    private int fallbackResource;}
```