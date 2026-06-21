#!/usr/bin/env python3
"""
Script untuk fix encoding Java files ke UTF-8 tanpa BOM.
Jalankan jika ada error "illegal character: '\u0000'" saat compile.

Gunakan: python fix_encoding.py
"""

import os
import sys

def fix_java_files():
    """Konversi semua Java files ke UTF-8 tanpa BOM"""
    
    java_dirs = [
        'src/airforce/core',
        'src/airforce/entities',
        'src/airforce/ui',
        'src/airforce/utils'
    ]
    
    fixed_count = 0
    
    for java_dir in java_dirs:
        if not os.path.exists(java_dir):
            continue
            
        for filename in os.listdir(java_dir):
            if filename.endswith('.java'):
                filepath = os.path.join(java_dir, filename)
                
                try:
                    # Baca file dengan berbagai encoding
                    with open(filepath, 'rb') as f:
                        content = f.read()
                    
                    # Coba decode dengan UTF-16 LE terlebih dahulu
                    if content.startswith(b'\xff\xfe'):  # UTF-16 LE BOM
                        text = content.decode('utf-16-le')
                        print(f"Converting {filepath} from UTF-16 LE to UTF-8...")
                    else:
                        # Coba UTF-8 dulu
                        try:
                            text = content.decode('utf-8')
                        except:
                            # Fallback ke Latin-1
                            text = content.decode('latin-1')
                    
                    # Tulis ulang sebagai UTF-8 tanpa BOM
                    with open(filepath, 'w', encoding='utf-8') as f:
                        f.write(text)
                    
                    print(f"✓ Fixed: {filepath}")
                    fixed_count += 1
                    
                except Exception as e:
                    print(f"✗ Error fixing {filepath}: {e}")
                    return False
    
    print(f"\n✓ Total files fixed: {fixed_count}")
    return True

if __name__ == '__main__':
    print("=== Fixing Java File Encoding ===\n")
    
    if fix_java_files():
        print("\n✓ All files converted to UTF-8!")
        print("\nSekarang coba compile lagi:")
        print("  Windows: compile.bat")
        print("  Linux/Mac: ./compile.sh")
    else:
        print("\n✗ Failed to fix encoding")
        sys.exit(1)