# Instructions to Download Queen Image

Since we cannot directly download the image programmatically, please follow these steps:

## Option 1: Download the Image
1. Open your browser
2. Go to: https://liveexample.pearsoncmg.com/common/image/queen.jpg
3. Right-click on the image and select "Save image as..."
4. Save it as `queen.png` in this directory:
   `src/main/resources/com/csc3402/lab/ccs3104_lab/LAB4/`

## Option 2: Use PowerShell to Download (Quick Method)
Run this command in PowerShell from the project root:

```powershell
Invoke-WebRequest -Uri "https://liveexample.pearsoncmg.com/common/image/queen.jpg" -OutFile "src/main/resources/com/csc3402/lab/ccs3104_lab/LAB4/queen.png"
```

## Option 3: Use the Unicode Symbol
The program already has a fallback mechanism that uses the Unicode chess queen symbol (♛) if the image is not found. This works perfectly fine and looks good on the modern UI!

## Note
The application will work with or without the image. If the image is not present, it will automatically use the red chess queen symbol (♛) which looks great on the light blue theme.
